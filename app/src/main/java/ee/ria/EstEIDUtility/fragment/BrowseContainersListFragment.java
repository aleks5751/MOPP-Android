package ee.ria.EstEIDUtility.fragment;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import ee.ria.EstEIDUtility.R;
import ee.ria.EstEIDUtility.activity.BdocDetailActivity;
import ee.ria.EstEIDUtility.adapter.BdocAdapter;
import ee.ria.EstEIDUtility.domain.BdocItem;
import ee.ria.EstEIDUtility.util.Constants;
import ee.ria.EstEIDUtility.util.DateUtils;
import ee.ria.EstEIDUtility.util.FileUtils;

public class BrowseContainersListFragment extends ListFragment {

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        launchBdocDetailActivity(position);
    }

    private void launchBdocDetailActivity(int position) {
        BdocItem bdocItem = (BdocItem) getListAdapter().getItem(position);
        Intent intent = new Intent(getActivity(), BdocDetailActivity.class);
        intent.putExtra(Constants.BDOC_NAME, bdocItem.getName());
        startActivity(intent);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        List<BdocItem> bdocs = getBdocFiles();

        final BdocAdapter bdocAdapter = new BdocAdapter(getActivity(), bdocs);
        setListAdapter(bdocAdapter);

        SearchView searchView = (SearchView) getActivity().findViewById(R.id.listSearch);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                bdocAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    private List<BdocItem> getBdocFiles() {
        List<BdocItem> bdocs = new ArrayList<>();

        List<File> bdocFiles = getBdocContainers();
        for (File file : bdocFiles) {
            String fileCreated = getFileLastModified(file);
            bdocs.add(new BdocItem(file.getName(), fileCreated));
        }

        return bdocs;
    }

    private String getFileLastModified(File file) {
        Date fileModified = new Date(file.lastModified());

        if (DateUtils.isToday(fileModified)) {
            return DateUtils.TODAY_FORMAT.format(fileModified);
        } else if (DateUtils.isYesterday(fileModified)) {
            return getResources().getString(R.string.activity_browse_containers_yesterday);
        } else if (DateUtils.isCurrentYear(fileModified)) {
            return DateUtils.CURRENT_YEAR_FORMAT.format(fileModified);
        }

        return DateUtils.DATE_FORMAT.format(fileModified);
    }

    private List<File> getBdocContainers() {
        File bdocsPath = FileUtils.getBdocsPath(getActivity().getFilesDir());
        File[] bdocFiles = bdocsPath.listFiles();

        if (bdocFiles == null) {
            return Collections.emptyList();
        }

        List<File> bdocs = new ArrayList<>();
        for (File bdoc : bdocFiles) {
            if (FilenameUtils.getExtension(bdoc.getName()).equals(Constants.BDOC_EXTENSION)) {
                bdocs.add(bdoc);
            }
        }
        return bdocs;
    }

}