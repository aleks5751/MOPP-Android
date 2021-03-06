package ee.ria.DigiDoc.android.model.idcard;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;

import ee.ria.tokenlibrary.Token;

@AutoValue
public abstract class IdCardDataResponse {

    @IdCardStatus public abstract String status();

    @Nullable public abstract IdCardData data();

    @Nullable public abstract Throwable error();

    @Nullable public abstract Token token();

    public static IdCardDataResponse initial() {
        return create(IdCardStatus.INITIAL, null, null, null);
    }

    public static IdCardDataResponse readerDetected() {
        return create(IdCardStatus.READER_DETECTED, null, null, null);
    }

    public static IdCardDataResponse cardDetected() {
        return create(IdCardStatus.CARD_DETECTED, null, null, null);
    }

    public static IdCardDataResponse success(IdCardData data, Token token) {
        return create(IdCardStatus.CARD_DETECTED, data, null, token);
    }

    public static IdCardDataResponse failure(Throwable error) {
        return create(IdCardStatus.CARD_DETECTED, null, error, null);
    }

    private static IdCardDataResponse create(@IdCardStatus String status,
                                             @Nullable IdCardData data, @Nullable Throwable error,
                                             @Nullable Token token) {
        return new AutoValue_IdCardDataResponse(status, data, error, token);
    }
}
