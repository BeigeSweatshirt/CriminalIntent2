package android.beige.criminalintent;

import java.util.Date;
import java.util.UUID;

class Crime {
    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;
    private boolean mRequiresPolice;

    Crime() {
        mId = UUID.randomUUID();
        mDate = new Date();
    }

    UUID getId() {
        return mId;
    }

    String getTitle() {
        return mTitle;
    }

    Date getDate() {
        return mDate;
    }

    boolean isSolved() {
        return mSolved;
    }

    void setTitle(String title) {
        mTitle = title;
    }

    void setDate(Date date) {
        mDate = date;
    }

    void setSolved(boolean solved) {
        mSolved = solved;
    }

    boolean isPoliceRequired() {
        return mRequiresPolice;
    }

    void setRequiresPolice(boolean requiresPolice) {
        mRequiresPolice = requiresPolice;
    }
}
