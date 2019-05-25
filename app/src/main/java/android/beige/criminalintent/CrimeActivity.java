package android.beige.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

class CrimeActivity extends SingleFragmentActivity {
    private static final String EXTRA_CRIME_ID = "com.android.beige.criminalintent.crime_id";

    @Override
    Fragment createFragment() {
        UUID crimeId = (UUID) getIntent().getSerializableExtra(CrimeActivity.EXTRA_CRIME_ID);
        return CrimeFragment.newInstance(crimeId);
    }

    static Intent newIntent(Context packageContext, UUID crimeId) {
        Intent intent = new Intent(packageContext, CrimeActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }
}
