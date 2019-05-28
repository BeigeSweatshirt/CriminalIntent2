package android.beige.criminalintent;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

class Crime {
    private UUID mId;
    private String mTitle;
    private Calendar mDate;
    private boolean mSolved;
    private boolean mRequiresPolice;

    Crime() {
        mId = UUID.randomUUID();
        mDate = Calendar.getInstance();
    }

    UUID getId() {
        return mId;
    }

    String getTitle() {
        return mTitle;
    }

    void setTitle(String title) {
        mTitle = title;
    }

    Calendar getDate() {
        return mDate;
    }

    void setDate(Calendar date) {
        mDate = date;
    }

    boolean getIsSolved() {
        return mSolved;
    }

    void setIsSolved(boolean solved) {
        mSolved = solved;
    }

    boolean getIsPoliceRequired() {
        return mRequiresPolice;
    }

    void setIsRequiresPolice(boolean requiresPolice) {
        mRequiresPolice = requiresPolice;
    }
}
