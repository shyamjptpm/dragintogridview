package com.example.androidimageviewlist;

import java.util.ArrayList;
import java.util.List;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.method.ScrollingMovementMethod;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {


	//List<Item> items1, items2, items3,items4;
	List<Item> playerlist, boatpos,frontdrummer,backdrummer;
	//ListView listView1, listView2, listView3;
	GridView gridView1,gridView2,gridfront,gridback;
	ItemsListAdapter myItemsListAdapter1, myItemsListAdapter2, myItemsListAdapter3,myItemsListAdapter4;
	//LinearLayoutListView area1, area2, area3;
	LinearLayoutGridView grid1,grid2,front,back;
	TextView prompt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
//		listView1 = (ListView)findViewById(R.id.listview1);
//		listView2 = (ListView)findViewById(R.id.listview2);
//		listView3 = (ListView)findViewById(R.id.listview3);
		ActionBar actionBar = getSupportActionBar();
		actionBar.setTitle("Dragon King Boat");
		gridView1  = (GridView)findViewById(R.id.gridview1);
		gridView2  = (GridView)findViewById(R.id.gridview2);
		gridfront  = (GridView)findViewById(R.id.drummer_front);
		gridback  = (GridView)findViewById(R.id.drummer_back);
//		area1 = (LinearLayoutListView)findViewById(R.id.pane1);
//		area2 = (LinearLayoutListView)findViewById(R.id.pane2);
//		area3 = (LinearLayoutListView)findViewById(R.id.pane3);
		grid1 = (LinearLayoutGridView)findViewById(R.id.member_list);
		grid2 = (LinearLayoutGridView)findViewById(R.id.boat);
		front = (LinearLayoutGridView)findViewById(R.id.boat_front);
		back = (LinearLayoutGridView)findViewById(R.id.boat_back);
//		area1.setOnDragListener(myOnDragListener);
//		area2.setOnDragListener(myOnDragListener);
//		area3.setOnDragListener(myOnDragListener);
		grid1.setOnDragListener(myOnDragListener);
		grid2.setOnDragListener(myOnDragListener);
		front.setOnDragListener(myOnDragListener);
		back.setOnDragListener(myOnDragListener);
//		area1.setListView(listView1);
//		area2.setListView(listView2);
//		area3.setListView(listView3);
		grid1.setGridView(gridView1);
		grid2.setGridView(gridView2);
		front.setGridView(gridfront);
		back.setGridView(gridback);

		
		initItems();
		myItemsListAdapter1 = new ItemsListAdapter(this, playerlist);
		myItemsListAdapter2 = new ItemsListAdapter(this, boatpos);
		myItemsListAdapter3 = new ItemsListAdapter(this, frontdrummer);
		myItemsListAdapter4 = new ItemsListAdapter(this, backdrummer);
//		listView1.setAdapter(myItemsListAdapter1);


		gridView1.setAdapter(myItemsListAdapter1);
		gridView2.setAdapter(myItemsListAdapter2);
		gridfront.setAdapter(myItemsListAdapter3);
		gridback.setAdapter(myItemsListAdapter4);
		//Auto scroll to end of ListView
//		listView1.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
//		listView2.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
//		listView3.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);

//		listView1.setOnItemClickListener(listOnItemClickListener);
//		listView2.setOnItemClickListener(listOnItemClickListener);
//		listView3.setOnItemClickListener(listOnItemClickListener);
		gridView1.setOnItemClickListener(listOnItemClickListener);
		gridView2.setOnItemClickListener(listOnItemClickListener);
		gridfront.setOnItemClickListener(listOnItemClickListener);
		gridback.setOnItemClickListener(listOnItemClickListener);

//		listView1.setOnItemLongClickListener(myOnItemLongClickListener);
//		listView2.setOnItemLongClickListener(myOnItemLongClickListener);
//		listView3.setOnItemLongClickListener(myOnItemLongClickListener);
		gridView1.setOnItemLongClickListener(myOnItemLongClickListener);
		gridView2.setOnItemLongClickListener(myOnItemLongClickListener);
		gridfront.setOnItemLongClickListener(myOnItemLongClickListener);
		gridback.setOnItemLongClickListener(myOnItemLongClickListener);

		prompt = (TextView) findViewById(R.id.prompt);
		// make TextView scrollable
		prompt.setMovementMethod(new ScrollingMovementMethod());
		//clear prompt area if LongClick
		prompt.setOnLongClickListener(new OnLongClickListener(){
			
			@Override
			public boolean onLongClick(View v) {
				prompt.setText("");
				return true;	
			}});

	}
	
	OnItemLongClickListener myOnItemLongClickListener = new OnItemLongClickListener(){

		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view,
				int position, long id) {
			Item selectedItem = (Item)(parent.getItemAtPosition(position));
			
			ItemsListAdapter associatedAdapter = (ItemsListAdapter)(parent.getAdapter());
		    List<Item> associatedList = associatedAdapter.getList();
			
			PassObject passObj = new PassObject(view, selectedItem, associatedList);
			
			
			ClipData data = ClipData.newPlainText("", "");
			DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
			view.startDrag(data, shadowBuilder, passObj, 0);
			
			return true;
		}
		
	};
	
	OnDragListener myOnDragListener = new OnDragListener() {

		@Override
		public boolean onDrag(View v, DragEvent event) {
			String area;
			if(v == grid1){
				area = "Players list";
			}else if(v == grid2){
				area = "Boat list";

			}else if(v == front){
				area = "Boat front";

			}else if(v == back){
				area = "Boat back";

			}else{
				area = "unknown";	
			}
			
			switch (event.getAction()) {
				case DragEvent.ACTION_DRAG_STARTED:
					prompt.append("ACTION_DRAG_STARTED: " + area  + "\n");
					break;	
				case DragEvent.ACTION_DRAG_ENTERED:
					prompt.append("ACTION_DRAG_ENTERED: " + area  + "\n");
					break;	
				case DragEvent.ACTION_DRAG_EXITED:
					prompt.append("ACTION_DRAG_EXITED: " + area  + "\n");
					break;	
				case DragEvent.ACTION_DROP:
					prompt.append("ACTION_DROP: " + area  + "\n");

					PassObject passObj = (PassObject)event.getLocalState();
					View view = passObj.view;
					Item passedItem = passObj.item;
					List<Item> srcList = passObj.srcList;
					//ListView oldParent = (ListView)view.getParent();
					GridView oldParent = (GridView)view.getParent();
					ItemsListAdapter srcAdapter = (ItemsListAdapter)(oldParent.getAdapter());

						/*LinearLayoutListView newParent = (LinearLayoutListView) v;
						ItemsListAdapter destAdapter = (ItemsListAdapter) (newParent.listView.getAdapter());
						List<Item> destList = destAdapter.getList();

						if (removeItemToList(srcList, passedItem)) {
							addItemToList(destList, passedItem);
						}

						srcAdapter.notifyDataSetChanged();
						destAdapter.notifyDataSetChanged();*/


						LinearLayoutGridView newParent = (LinearLayoutGridView) v;
						ItemsListAdapter destAdapter = (ItemsListAdapter) (newParent.gridView.getAdapter());
						List<Item> destList = destAdapter.getList();

						if (removeItemToList(srcList, passedItem)) {
							addItemToList(destList, passedItem);
						}

						srcAdapter.notifyDataSetChanged();
						destAdapter.notifyDataSetChanged();

					break;
			   case DragEvent.ACTION_DRAG_ENDED:
				   prompt.append("ACTION_DRAG_ENDED: " + area  + "\n");   
			   default:
				   break;	   
			}
			   
			return true;
		}
		
	};
	
	OnItemClickListener listOnItemClickListener = new OnItemClickListener(){

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Toast.makeText(MainActivity.this, 
					((Item)(parent.getItemAtPosition(position))).ItemString, 
					Toast.LENGTH_SHORT).show();
		}
		
	};

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	private void initItems(){
		playerlist = new ArrayList<Item>();
		boatpos = new ArrayList<Item>();
		frontdrummer = new ArrayList<Item>();
		backdrummer = new ArrayList<Item>();

		TypedArray arrayDrawable = getResources().obtainTypedArray(R.array.resicon);
		TypedArray arrayText = getResources().obtainTypedArray(R.array.restext);
		Drawable d = getDrawable(android.R.drawable.ic_menu_myplaces);
		for(int i=0; i<arrayDrawable.length(); i++){
			//Drawable d = arrayDrawable.getDrawable(i);

			String s = arrayText.getString(i);
			Item item = new Item(d, s);
			playerlist.add(item);
		}
		
		arrayDrawable.recycle();
		arrayText.recycle();
	}
	
	private boolean removeItemToList(List<Item> l, Item it){
		boolean result = l.remove(it);
		return result;
	}
	
	private boolean addItemToList(List<Item> l, Item it){
		boolean result = l.add(it);
		return result;
	}
	//items stored in ListView
	public class Item {
		Drawable ItemDrawable;
		String ItemString;
		Item(Drawable drawable, String t){
			ItemDrawable = drawable;
			ItemString = t;
		}
	}

	//objects passed in Drag and Drop operation
	class PassObject{
		View view;
		Item item;
		List<Item> srcList;

		PassObject(View v, Item i, List<Item> s){
			view = v;
			item = i;
			srcList = s;
		}
	}

	static class ViewHolder {
		ImageView icon;
		TextView text;
	}

	public class ItemsListAdapter extends BaseAdapter {

		private Context context;
		private List<Item> list;

		ItemsListAdapter(Context c, List<Item> l){
			context = c;
			list = l;
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View rowView = convertView;

			// reuse views
			if (rowView == null) {
				LayoutInflater inflater = ((Activity) context).getLayoutInflater();
				rowView = inflater.inflate(R.layout.row, null);

				ViewHolder viewHolder = new ViewHolder();
				viewHolder.icon = (ImageView) rowView.findViewById(R.id.rowImageView);
				viewHolder.text = (TextView) rowView.findViewById(R.id.rowTextView);
				rowView.setTag(viewHolder);
			}

			ViewHolder holder = (ViewHolder) rowView.getTag();
			holder.icon.setImageDrawable(list.get(position).ItemDrawable);
			holder.text.setText(list.get(position).ItemString);

			return rowView;
		}

		public List<Item> getList(){
			return list;
		}
	}

}
