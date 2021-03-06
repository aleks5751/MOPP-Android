package ee.ria.DigiDoc.android.crypto;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;

import ee.ria.DigiDoc.R;
import ee.ria.DigiDoc.android.main.home.HomeToolbar;

public final class CryptoHomeView extends CoordinatorLayout implements
        HomeToolbar.HomeToolbarAware {

    private final HomeToolbar toolbarView;

    public CryptoHomeView(Context context) {
        this(context, null);
    }

    public CryptoHomeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CryptoHomeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.crypto_home, this);
        toolbarView = findViewById(R.id.toolbar);
    }

    @Override
    public HomeToolbar homeToolbar() {
        return toolbarView;
    }
}
