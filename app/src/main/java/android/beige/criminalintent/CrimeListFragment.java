package android.beige.criminalintent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CrimeListFragment extends Fragment {
    private static final int IS_CRIME_BENIGN = 0;
    private static final int IS_CRIME_SERIOUS = 1;
    private RecyclerView mCrimeRecyclerView;
    private static CrimeAdapter mAdapter;
    private int mLastUpdatePosition = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);
        mCrimeRecyclerView = view.findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();

        if (mAdapter == null) {
            mAdapter = new CrimeAdapter(crimes);
            mCrimeRecyclerView.setAdapter(mAdapter);
        } else {
            if (mLastUpdatePosition > -1) {
                mAdapter.notifyItemChanged(mLastUpdatePosition);
                mLastUpdatePosition = -1;
            } else {
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {
        private List<Crime> mCrimes;

        CrimeAdapter(List<Crime> crimes) {
            mCrimes = crimes;
        }

        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            if (viewType == IS_CRIME_BENIGN) return new CrimeHolderBenign(layoutInflater, parent);
            else return new CrimeHolderSerious(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            Crime crime = mCrimes.get(position);
            holder.bind(crime);
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }

        @Override
        public int getItemViewType(int position) {
            int viewType;

            if (!mCrimes.get(position).getIsPoliceRequired()) viewType = IS_CRIME_BENIGN;
            else viewType = IS_CRIME_SERIOUS;
            return viewType;
        }
    }

    private abstract class CrimeHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        Crime mCrime;
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private ImageView mSolvedImageView;

        CrimeHolder(LayoutInflater inflater, ViewGroup parent, int layout) {
            super(inflater.inflate(layout, parent, false));
            itemView.setOnClickListener(this);

            mTitleTextView = itemView.findViewById(R.id.crime_title);
            mDateTextView = itemView.findViewById(R.id.crime_date);
            mSolvedImageView = itemView.findViewById(R.id.crime_solved);
        }

        public void bind(Crime crime) {
            mCrime = crime;
            String date;
            mTitleTextView.setText(mCrime.getTitle());

            date = android.text.format.DateFormat
                    .format("dd/MM/yyyy 'at' hh:mm a", mCrime.getDate()).toString();
            mDateTextView.setText(date);

            mSolvedImageView.setVisibility(crime.getIsSolved() ? View.VISIBLE : View.GONE);
        }

        @Override
        public void onClick(View view) {
            Intent intent = CrimePagerActivity.newIntent(getActivity(), mCrime.getId());
            mLastUpdatePosition = this.getAdapterPosition();
            startActivity(intent);
        }
    }

    private class CrimeHolderBenign extends CrimeHolder {

        CrimeHolderBenign(LayoutInflater inflater, ViewGroup parent) {
            super(inflater, parent, R.layout.list_item_crime);
        }
    }

    private class CrimeHolderSerious extends CrimeHolder {

        private Button mContactPoliceButton;

        CrimeHolderSerious(LayoutInflater inflater, ViewGroup parent) {
            super(inflater, parent, R.layout.list_item_crime_serious);
        }

        @Override
        public void bind(final Crime crime) {
            super.bind(crime);

            mContactPoliceButton = itemView.findViewById(R.id.contact_police_button);
            if (crime.getIsSolved()) {
                mContactPoliceButton.setVisibility(View.GONE);
            } else {
                mContactPoliceButton.setVisibility(View.VISIBLE);
            }
            mContactPoliceButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String policeToast = getString(R.string.crime_police_toast) + crime.getTitle();

                    Toast.makeText(getActivity(), policeToast, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}

