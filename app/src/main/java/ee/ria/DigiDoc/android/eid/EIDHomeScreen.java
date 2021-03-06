package ee.ria.DigiDoc.android.eid;

import android.content.Context;
import android.view.View;

import ee.ria.DigiDoc.R;
import ee.ria.DigiDoc.android.utils.navigator.conductor.ConductorScreen;

public final class EIDHomeScreen extends ConductorScreen {

    public static EIDHomeScreen create() {
        return new EIDHomeScreen();
    }

    @SuppressWarnings("WeakerAccess")
    public EIDHomeScreen() {
        super(R.id.eidHomeScreen);
    }

    @Override
    protected View view(Context context) {
        return new EIDHomeView(context, getInstanceId());
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this || obj instanceof EIDHomeScreen;
    }
}
