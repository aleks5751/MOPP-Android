package ee.ria.DigiDoc.android.model.idcard;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;

import ee.ria.mopplib.data.SignedContainer;
import ee.ria.tokenlibrary.Token;

@AutoValue
public abstract class IdCardSignResponse {

    public abstract boolean active();

    @Nullable public abstract SignedContainer container();

    @Nullable public abstract Throwable error();

    @Nullable public abstract IdCardData data();

    @Nullable public abstract Token token();

    public static IdCardSignResponse activity() {
        return create(true, null, null, null, null);
    }

    public static IdCardSignResponse success(SignedContainer container) {
        return create(false, container, null, null, null);
    }

    public static IdCardSignResponse failure(Throwable error, IdCardData data, Token token) {
        return create(false, null, error, data, token);
    }

    private static IdCardSignResponse create(boolean active, @Nullable SignedContainer container,
                                             @Nullable Throwable error, @Nullable IdCardData data,
                                             @Nullable Token token) {
        return new AutoValue_IdCardSignResponse(active, container, error, data, token);
    }
}
