package com.example.imagedemo;

import java.util.ArrayList;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap.Config;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 棣栭〉ListView鐨勬暟鎹�傞厤鍣�
 * 
 * @author Administrator
 * 
 */
public class ListItemAdapter extends BaseAdapter {

	private Context mContext;
	private ArrayList<ItemEntity> items;

	public ListItemAdapter(Context ctx, ArrayList<ItemEntity> items) {
		this.mContext = ctx;
		this.items = items;
	}

	@Override
	public int getCount() {
		return items == null ? 0 : items.size();
	}

	@Override
	public Object getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = View.inflate(mContext, R.layout.item_list, null);
			holder.iv_avatar = (ImageView) convertView.findViewById(R.id.iv_avatar);
			holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
			holder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
			holder.gridview = (NoScrollGridView) convertView.findViewById(R.id.gridview);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		ItemEntity itemEntity = items.get(position);
		holder.tv_title.setText(itemEntity.getTitle());
		holder.tv_content.setText(itemEntity.getContent());
		// 浣跨敤ImageLoader鍔犺浇缃戠粶鍥剧墖
		DisplayImageOptions options = new DisplayImageOptions.Builder()//
				.showImageOnLoading(R.drawable.ic_launcher) // 鍔犺浇涓樉绀虹殑榛樿鍥剧墖
				.showImageOnFail(R.drawable.ic_launcher) // 璁剧疆鍔犺浇澶辫触鐨勯粯璁ゅ浘鐗�
				.cacheInMemory(true) // 鍐呭瓨缂撳瓨
				.cacheOnDisk(true) // sdcard缂撳瓨
				.bitmapConfig(Config.RGB_565)// 璁剧疆鏈�浣庨厤缃�
				.build();//
		ImageLoader.getInstance().displayImage(itemEntity.getAvatar(), holder.iv_avatar, options);
		final ArrayList<String> imageUrls = itemEntity.getImageUrls();
		if (imageUrls == null || imageUrls.size() == 0) { // 娌℃湁鍥剧墖璧勬簮灏遍殣钘廏ridView
			holder.gridview.setVisibility(View.GONE);
		} else {
			holder.gridview.setAdapter(new NoScrollGridAdapter(mContext, imageUrls));
		}
		// 鐐瑰嚮鍥炲笘涔濆鏍硷紝鏌ョ湅澶у浘
		holder.gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				imageBrower(position, imageUrls);
			}
		});
		return convertView;
	}

	/**
	 * 鎵撳紑鍥剧墖鏌ョ湅鍣�
	 * 
	 * @param position
	 * @param urls2
	 */
	protected void imageBrower(int position, ArrayList<String> urls2) {
		Intent intent = new Intent(mContext, ImagePagerActivity.class);
		// 鍥剧墖url,涓轰簡婕旂ず杩欓噷浣跨敤甯搁噺锛屼竴鑸粠鏁版嵁搴撲腑鎴栫綉缁滀腑鑾峰彇
		intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urls2);
		intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
		mContext.startActivity(intent);
	}

	/**
	 * listview缁勪欢澶嶇敤锛岄槻姝⑩�滃崱椤库��
	 * 
	 * @author Administrator
	 * 
	 */
	class ViewHolder {
		private ImageView iv_avatar;
		private TextView tv_title;
		private TextView tv_content;
		private NoScrollGridView gridview;
	}
}
