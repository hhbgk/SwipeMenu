package com.hhbgk.swipemenu;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.allen.expandablelistview.BaseSwipeMenuExpandableListAdapter;
import com.allen.expandablelistview.SwipeMenuExpandableCreator;
import com.allen.expandablelistview.SwipeMenuExpandableListAdapter;
import com.allen.expandablelistview.SwipeMenuExpandableListView;
import com.baoyz.swipemenulistview.ContentViewWrapper;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListViewActivity extends AppCompatActivity {
    private String tag = getClass().getSimpleName();
    private AppAdapter mAdapter;
    private SwipeMenuExpandableListView listView;
    private Toast mToast;

    private List<String> prepareGroupData(int deviceNumber){
        List<String> headerData = new ArrayList<>();
        for (int i=0; i < deviceNumber; i++) {
            headerData.add("uuid " + i);
        }
        return headerData;
    }
    private HashMap<String, List<String>> prepareData(List<String> groupData, int userNumber){

        HashMap<String, List<String>> data = new HashMap<>();
        for (int i = 0; i < groupData.size(); i++){
            List<String> deviceGroup = new ArrayList<>();
            for (int j = 0; j < userNumber; j++){
                deviceGroup.add("user "+j);
            }
            data.put(groupData.get(i), deviceGroup);
        }
        return data;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable_list_view);

        // 1. Create View
        listView = (SwipeMenuExpandableListView) findViewById(R.id.listView);

        // 3. Create Adapter and set. It controls data,
        // item view,clickable ,swipable ...
        final List<String> groupData = prepareGroupData(2);
        mAdapter = new AppAdapter(this, groupData, prepareData(groupData, 3));
        listView.setAdapter(mAdapter);

        // 4. Set OnItemLongClickListener
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                int itemType = ExpandableListView.getPackedPositionType(id);
                int childPosition, groupPosition;
                if (itemType == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
                    childPosition = ExpandableListView.getPackedPositionChild(id);
                    groupPosition = ExpandableListView.getPackedPositionGroup(id);

                    // do your per-item callback here
                    showToast("long click on group " + groupPosition + " child " + childPosition);
                    return true; // true if we consumed the click, false if not

                } else if (itemType == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
                    groupPosition = ExpandableListView.getPackedPositionGroup(id);
                    // do your per-group callback here
                    showToast("long click on group " + groupPosition);
                    return true; // true if we consumed the click, false if not

                } else {
                    // null item; we don't consume the click
                    return false;
                }
            }
        });

        // 5.Set OnGroupClickListener
        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                showToast("group " + groupPosition + " clicked");
                return false;
            }
        });

        // 6.Set OnChildClickListener
        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                showToast("group " + groupPosition + " child " + childPosition + " clicked");
                return false;
            }
        });

        // 7. Create and set a SwipeMenuExpandableCreator
        // define the group and child creator function
        SwipeMenuExpandableCreator creator = new SwipeMenuExpandableCreator() {
            @Override
            public void createGroup(SwipeMenu menu) {
                // Create different menus depending on the view type
                Log.e(tag, "createGroup************type="+menu.getViewType());
            }

            @Override
            public void createChild(SwipeMenu menu) {
                Log.e(tag, "createChild===========type=" + menu.getViewType());
                // Create different menus depending on the view type
                switch (menu.getViewType()) {
                    case 0:
                        createMenu1(menu);
                        break;
                }
            }

            private void createMenu1(SwipeMenu menu) {
                SwipeMenuItem item1 = new SwipeMenuItem(getApplicationContext());
                item1.setBackground(new ColorDrawable(Color.rgb(0xE5, 0x18, 0x5E)));
                item1.setWidth(dp2px(90));
                item1.setTitle("delete");
                item1.setTitleColor(Color.BLACK);
                item1.setTitleSize(20);
                //item1.setIcon(R.mipmap.ic_action_favorite);
                menu.addMenuItem(item1);
/*
                SwipeMenuItem item2 = new SwipeMenuItem(getApplicationContext());
                item2.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9, 0xCE)));
                item2.setWidth(dp2px(90));
                //item2.setIcon(R.mipmap.ic_action_good);
                menu.addMenuItem(item2);*/
            }
        };
        listView.setMenuCreator(creator);

        // 8. Set OnMenuItemClickListener
        listView.setOnMenuItemClickListener(new SwipeMenuExpandableListView.OnMenuItemClickListenerForExpandable() {
            @Override
            public boolean onMenuItemClick(int groupPostion, int childPostion, SwipeMenu menu, int index) {
                // childPostion == -1991 means it was the group's swipe menu was
                // clicked
                String s = "Group " + groupPostion;
                if (childPostion != SwipeMenuExpandableListAdapter.GROUP_INDEX) {
                    s += " , child " + childPostion;
                }
                s += " , menu index:" + index + " was clicked";
                switch (index) {
                    case 0:
                        // open
                        break;
                    case 1:
                        // delete
                        // delete(item);
                        mAdapter.notifyDataSetChanged();
                        break;
                }
                showToast(s);
                return false;
            }
        });

    }

    private void showToast(String s){
        if (mToast == null){
            mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        }
        mToast.setText(s);
        mToast.show();
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    /**
     * Basically, it's the same as BaseExpandableListAdapter. But added controls
     * to every item's swipability
     *
     * @author yuchentang
     * @see android.widget.BaseExpandableListAdapter
     */
    class AppAdapter extends BaseSwipeMenuExpandableListAdapter {
        private Context mContext;
        private List<String> mGroupData;
        private HashMap<String, List<String>> mChildData;

        public AppAdapter(Context context, List<String> groupData, HashMap<String, List<String>> childData){
            mContext = context;
            mGroupData = groupData;
            mChildData = childData;
        }

        /**
         * Whether this group item swipable
         *
         * @param groupPosition
         * @return
         * @see com.allen.expandablelistview.BaseSwipeMenuExpandableListAdapter#isGroupSwipeable(int)
         */
        @Override
        public boolean isGroupSwipeable(int groupPosition) {
            return true;
        }

        /**
         * Whether this child item swipable
         *
         * @param groupPosition
         * @param childPosition
         * @return
         * @see com.allen.expandablelistview.BaseSwipeMenuExpandableListAdapter#isChildSwipeable(int,
         *      int)
         */
        @Override
        public boolean isChildSwipeable(int groupPosition, int childPosition) {
            if (childPosition == 0)
                return false;
            return true;
        }

/*        @Override
        public int getChildType(int groupPosition, int childPosition) {
            return childPosition % 3;
        }

        @Override
        public int getChildTypeCount() {
            return 3;
        }

        @Override
        public int getGroupType(int groupPosition) {
            return groupPosition % 3;
        }

        @Override
        public int getGroupTypeCount() {
            return 3;
        }*/

        class ViewHolder {
            ImageView iv_icon;
            TextView tv_name;

            public ViewHolder(View view) {
                iv_icon = (ImageView) view.findViewById(R.id.image_more);
                tv_name = (TextView) view.findViewById(R.id.text_title);
                view.setTag(this);
            }
        }

        class ChildViewHolder {
            TextView tv_name;

            public ChildViewHolder(View view) {
                tv_name = (TextView) view.findViewById(R.id.user_info);
                view.setTag(this);
            }
        }

        @Override
        public int getGroupCount() {
//        Log.w(tag, "group count " + mGroupData.size());
            if (mGroupData == null)
                return 0;
            else
                return mGroupData.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
//        Log.w(tag, "child count " +mChildData.get(mGroupData.get(groupPosition)).size());
            if (mGroupData == null || mChildData.get(mGroupData.get(groupPosition)) == null){
                return 0;
            } else {
                return mChildData.get(mGroupData.get(groupPosition)).size();
            }
        }

        @Override
        public Object getGroup(int groupPosition) {
            return mGroupData == null ? null : mGroupData.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return mGroupData == null ? null:mChildData.get(mGroupData.get(groupPosition)).get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public ContentViewWrapper getGroupViewAndReUsable(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            boolean reUseable = true;
            if (convertView == null) {
                convertView = View.inflate(getApplicationContext(), R.layout.item_parent_layout, null);
                convertView.setTag(new ViewHolder(convertView));
                reUseable = false;
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();

            holder.tv_name.setText(mGroupData.get(groupPosition));
            return new ContentViewWrapper(convertView, reUseable);
        }

        @Override
        public ContentViewWrapper getChildViewAndReUsable(int groupPosition, int childPosition, boolean isLastChild, View convertView,
                                                          ViewGroup parent) {
            boolean reUseable = true;
            if (convertView == null) {
                convertView = View.inflate(getApplicationContext(), R.layout.item_child_layout, null);
                convertView.setTag(new ChildViewHolder(convertView));
                reUseable = false;
            }
            ChildViewHolder holder = (ChildViewHolder) convertView.getTag();
            if (null == holder) {
                holder = new ChildViewHolder(convertView);
            }

            holder.tv_name.setText(mChildData.get(mGroupData.get(groupPosition)).get(childPosition));
            return new ContentViewWrapper(convertView, reUseable);
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }

}
