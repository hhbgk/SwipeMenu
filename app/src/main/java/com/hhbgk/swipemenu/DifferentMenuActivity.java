package com.hhbgk.swipemenu;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.allen.expandablelistview.BaseSwipeMenuExpandableListAdapter;
import com.allen.expandablelistview.SwipeMenuExpandableCreator;
import com.allen.expandablelistview.SwipeMenuExpandableListAdapter;
import com.allen.expandablelistview.SwipeMenuExpandableListView;
import com.allen.expandablelistview.SwipeMenuExpandableListView.OnMenuItemClickListenerForExpandable;
import com.baoyz.swipemenulistview.ContentViewWrapper;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.List;

public class DifferentMenuActivity extends Activity {

    private List<ApplicationInfo> mAppList;
    private AppAdapter mAdapter;
    private SwipeMenuExpandableListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        mAppList = getPackageManager().getInstalledApplications(0);
        findViewById(R.id.btn).setOnClickListener(l);
        // 1. Create View
        listView = (SwipeMenuExpandableListView) findViewById(R.id.listView);

        if (getIntent().getBooleanExtra("stick_mode", false)) {
            // 2. Set where does the menu item stick with
            /**
             * STICK_TO_ITEM_RIGHT_SIDE: Stick with item right side, when swipe,
             * it moves from outside of screen .
             **/
            /**
             * STICK_TO_SCREEN: stick with the screen, it was covered and don't
             * move ,item moves then menu show.
             **/
            listView.setmMenuStickTo(SwipeMenuListView.STICK_TO_SCREEN);
        }
        // 3. Create Adapter and set. It controls data,
        // item view,clickable ,swipable ...
        mAdapter = new AppAdapter();
        listView.setAdapter(mAdapter);

        // 4. Set OnItemLongClickListener
        listView.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                int itemType = ExpandableListView.getPackedPositionType(id);
                int childPosition, groupPosition;
                if (itemType == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
                    childPosition = ExpandableListView.getPackedPositionChild(id);
                    groupPosition = ExpandableListView.getPackedPositionGroup(id);

                    // do your per-item callback here
                    Toast.makeText(DifferentMenuActivity.this,
                            "long click on group " + groupPosition + " child " + childPosition, Toast.LENGTH_SHORT)
                            .show();

                    return true; // true if we consumed the click, false if not

                } else if (itemType == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
                    groupPosition = ExpandableListView.getPackedPositionGroup(id);
                    // do your per-group callback here
                    Toast.makeText(DifferentMenuActivity.this, "long click on group " + groupPosition,
                            Toast.LENGTH_SHORT).show();
                    return true; // true if we consumed the click, false if not

                } else {
                    // null item; we don't consume the click
                    return false;
                }
            }
        });

        // 5.Set OnGroupClickListener
        listView.setOnGroupClickListener(new OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                Toast.makeText(DifferentMenuActivity.this, "group " + groupPosition + " clicked", Toast.LENGTH_SHORT)
                        .show();
                return false;
            }
        });

        // 6.Set OnChildClickListener
        listView.setOnChildClickListener(new OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Toast.makeText(DifferentMenuActivity.this,
                        "group " + groupPosition + " child " + childPosition + " clicked", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // 7. Create and set a SwipeMenuExpandableCreator
        // define the group and child creator function
        SwipeMenuExpandableCreator creator = new SwipeMenuExpandableCreator() {
            @Override
            public void createGroup(SwipeMenu menu) {
                // Create different menus depending on the view type
                switch (menu.getViewType()) {
                    case 0:
                        createMenu1(menu);
                        break;
                    case 1:
                        createMenu2(menu);
                        break;
                    case 2:
                        createMenu3(menu);
                        break;
                }
            }

            @Override
            public void createChild(SwipeMenu menu) {
                // Create different menus depending on the view type
                switch (menu.getViewType()) {
                    case 0:
                        createMenu1(menu);
                        break;
                    case 1:
                        createMenu2(menu);
                        break;
                    case 2:
                        createMenu3(menu);
                        break;
                }
            }

            private void createMenu1(SwipeMenu menu) {
                SwipeMenuItem item1 = new SwipeMenuItem(getApplicationContext());
                item1.setBackground(new ColorDrawable(Color.rgb(0xE5, 0x18, 0x5E)));
                item1.setWidth(dp2px(90));
                item1.setIcon(R.mipmap.ic_action_favorite);
                menu.addMenuItem(item1);
                SwipeMenuItem item2 = new SwipeMenuItem(getApplicationContext());
                item2.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9, 0xCE)));
                item2.setWidth(dp2px(90));
                item2.setIcon(R.mipmap.ic_action_good);
                menu.addMenuItem(item2);
            }

            private void createMenu2(SwipeMenu menu) {
                SwipeMenuItem item1 = new SwipeMenuItem(getApplicationContext());
                item1.setBackground(new ColorDrawable(Color.rgb(0xE5, 0xE0, 0x3F)));
                item1.setWidth(dp2px(90));
                item1.setIcon(R.mipmap.ic_action_important);
                menu.addMenuItem(item1);
                SwipeMenuItem item2 = new SwipeMenuItem(getApplicationContext());
                item2.setBackground(new ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25)));
                item2.setWidth(dp2px(90));
                item2.setIcon(R.mipmap.ic_action_discard);
                menu.addMenuItem(item2);
            }

            private void createMenu3(SwipeMenu menu) {
                SwipeMenuItem item1 = new SwipeMenuItem(getApplicationContext());
                item1.setBackground(new ColorDrawable(Color.rgb(0x30, 0xB1, 0xF5)));
                item1.setWidth(dp2px(90));
                item1.setIcon(R.mipmap.ic_action_about);
                menu.addMenuItem(item1);
                SwipeMenuItem item2 = new SwipeMenuItem(getApplicationContext());
                item2.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9, 0xCE)));
                item2.setWidth(dp2px(90));
                item2.setIcon(R.mipmap.ic_action_share);
                menu.addMenuItem(item2);
            }
        };
        listView.setMenuCreator(creator);

        // 8. Set OnMenuItemClickListener
        listView.setOnMenuItemClickListener(new OnMenuItemClickListenerForExpandable() {
            @Override
            public boolean onMenuItemClick(int groupPostion, int childPostion, SwipeMenu menu, int index) {
                ApplicationInfo item = mAppList.get(groupPostion);
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
                        mAppList.remove(groupPostion);
                        mAdapter.notifyDataSetChanged();
                        break;
                }
                Toast.makeText(DifferentMenuActivity.this, s, Toast.LENGTH_SHORT).show();
                return false;
            }
        });

    }

    public void btnClick(View view) {

    }
    /**
     * Basically, it's the same as BaseExpandableListAdapter. But added controls
     * to every item's swipability
     *
     * @author yuchentang
     * @see android.widget.BaseExpandableListAdapter
     */
    class AppAdapter extends BaseSwipeMenuExpandableListAdapter {

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

        @Override
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
        }

        class ViewHolder {
            ImageView iv_icon;
            TextView tv_name;

            public ViewHolder(View view) {
                iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
                tv_name = (TextView) view.findViewById(R.id.tv_name);
                view.setTag(this);
            }
        }

        @Override
        public int getGroupCount() {
            return mAppList.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return 3;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return mAppList.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return "The " + childPosition + "'th child in " + groupPosition + "'th group.";
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
        public ContentViewWrapper getGroupViewAndReUsable(int groupPosition, boolean isExpanded, View convertView,
                                                          ViewGroup parent) {
            boolean reUseable = true;
            if (convertView == null) {
                convertView = View.inflate(getApplicationContext(), R.layout.item_list_app, null);
                convertView.setTag(new ViewHolder(convertView));
                reUseable = false;
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();
            ApplicationInfo item = (ApplicationInfo) getGroup(groupPosition);
            holder.iv_icon.setImageDrawable(item.loadIcon(getPackageManager()));
            holder.tv_name.setText(item.loadLabel(getPackageManager()));
            return new ContentViewWrapper(convertView, reUseable);
        }

        @Override
        public ContentViewWrapper getChildViewAndReUsable(int groupPosition, int childPosition, boolean isLastChild, View convertView,
                                                          ViewGroup parent) {
            boolean reUseable = true;
            if (convertView == null) {
                convertView = View.inflate(getApplicationContext(), R.layout.item_list_app, null);
                convertView.setTag(new ViewHolder(convertView));
                reUseable = false;
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();
            if (null == holder) {
                holder = new ViewHolder(convertView);
            }

            ApplicationInfo item = (ApplicationInfo) getGroup(groupPosition);
            convertView.setBackgroundColor(Color.GRAY);
            holder.iv_icon.setImageDrawable(item.loadIcon(getPackageManager()));
            holder.tv_name.setText("this is child");
            return new ContentViewWrapper(convertView, reUseable);
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }
    OnClickListener l = new OnClickListener() {
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            mAdapter.notifyDataSetChanged(true);
        }
    };

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

}
