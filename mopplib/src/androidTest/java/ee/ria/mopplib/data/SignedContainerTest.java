package ee.ria.mopplib.data;

import android.content.res.AssetManager;
import android.support.test.InstrumentationRegistry;

import com.google.common.collect.ImmutableList;
import com.google.common.io.ByteStreams;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import static com.google.common.io.Files.getFileExtension;
import static com.google.common.io.Files.getNameWithoutExtension;
import static ee.ria.mopplib.SignedContainerSubject.assertThat;

public final class SignedContainerTest {

    private static final String DIR = "signed-containers";
    private static final String METADATA_EXTENSION = "json";

    @Rule public final ExpectedException exception = ExpectedException.none();
    @Rule public final TemporaryFolder folder =
            new TemporaryFolder(InstrumentationRegistry.getContext().getCacheDir());

    @Test public void open_matchesMetadata() throws Exception {
        AssetManager assetManager = InstrumentationRegistry.getTargetContext().getAssets();
        for (String metadataFileName : assetManager.list(DIR)) {
            if (!getFileExtension(metadataFileName).equals(METADATA_EXTENSION)) {
                continue;
            }
            String containerFileName = getNameWithoutExtension(metadataFileName);
            File containerFile = folder.newFile(containerFileName);
            try (
                    InputStream containerInputStream = assetManager
                            .open(DIR + "/" + containerFileName);
                    OutputStream containerOutputStream = new FileOutputStream(containerFile)
            ) {
                ByteStreams.copy(containerInputStream, containerOutputStream);
            }

            assertThat(containerFile)
                    .matchesMetadata(assetManager.open(DIR + "/" + metadataFileName));
        }
    }

    @Test public void open_fileDoesNotExist_throwsFileNotFoundException() throws Exception {
        exception.expect(FileNotFoundException.class);
        SignedContainer.open(folder.newFile());
    }

    @Test public void create_dataFilesNull_throwsContainerDataFilesEmptyException() throws
            Exception {
        exception.expect(ContainerDataFilesEmptyException.class);
        SignedContainer.create(folder.newFile(), null);
    }

    @Test public void create_dataFilesEmpty_throwsContainerDataFilesEmptyException() throws
            Exception {
        exception.expect(ContainerDataFilesEmptyException.class);
        SignedContainer.create(folder.newFile(), ImmutableList.of());
    }
}
