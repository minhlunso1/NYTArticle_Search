package minhna.submission.nytarticle_minh.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import minhna.submission.nytarticle_minh.MainActivity;

//http://guides.codepath.com/android/Endless-Scrolling-with-AdapterViews-and-RecyclerView
public abstract class EndlessScrollListener extends RecyclerView.OnScrollListener {
	StaggeredGridLayoutManager mStaggeredGridLayoutManager;
	int visibleThreshold;
	int lastVisibleItemPosition;

	public EndlessScrollListener(StaggeredGridLayoutManager layoutManager) {
		this.mStaggeredGridLayoutManager = layoutManager;
		visibleThreshold = visibleThreshold * mStaggeredGridLayoutManager.getSpanCount();
	}

	public int getLastVisibleItem(int[] lastVisibleItemPositions) {
		int maxSize = 0;
		for (int i = 0; i < lastVisibleItemPositions.length; i++) {
			if (i == 0) {
				maxSize = lastVisibleItemPositions[i];
			}
			else if (lastVisibleItemPositions[i] > maxSize) {
				maxSize = lastVisibleItemPositions[i];
			}
		}
		return maxSize;
	}

	@Override
	public void onScrolled(RecyclerView view, int dx, int dy) {
		int[] lastVisibleItemPositions = mStaggeredGridLayoutManager.findLastVisibleItemPositions(null);
		lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions);
		int visibleItemCount = view.getChildCount();
		int totalItemCount = mStaggeredGridLayoutManager.getItemCount();
//		Log.d("debug", "lastVisibleItemPosition:"+lastVisibleItemPosition);
//		Log.d("debug", "totalItemCount:"+ (totalItemCount- (MainActivity.queryPage-1)*10));
		if (lastVisibleItemPosition + MainActivity.queryPage >= totalItemCount && !MainActivity.isLoadingItem) {
			MainActivity.isLoadingItem=true;
			onLoadMore(++MainActivity.queryPage, totalItemCount);
		}
	}

	public abstract void onLoadMore(int page, int totalItemsCount);
}