package com.practice.loginregister;


import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.TextView;

        import java.util.ArrayList;

public class IngredientDateAdapter extends BaseAdapter {
    private static final int ITEM_VIEW_TYPE_STRS = 0;
    //  private static final int ITEM_VIEW_TYPE_IMGS = 1;
    // private static final int ITEM_VIEW_TYPE_MAX = 2;

    // 아이템 데이터 리스트.
    private ArrayList<DateListViewItem> DateListViewItemList = new ArrayList<DateListViewItem>();


    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return DateListViewItemList.size();
    }

     // 만약 listviewitem 에서 type 을 여러개로 ( 기한임박할때 구분) 나누었다면 썻을 함수

    // @Override
    //  public int getViewTypeCount() { //
    //     return ITEM_VIEW_TYPE_MAX;
//    }

    // position 위치의 아이템 타입 리턴.
    @Override
    public int getItemViewType(int position) {  // 기한 임박 나눌때 viewtype 정의하는 함수
        return DateListViewItemList.get(position).getType();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();
        int viewType = getItemViewType(position); // 기한임박한 아이템의 type 을 따로 저장받기 위해 쓴 함수

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
            DateListViewItem dateListViewItem = DateListViewItemList.get(position);

            //기간 임박할경우 나누기 위해 스위치문 으로 일단 구현
            switch (viewType) {
                case ITEM_VIEW_TYPE_STRS:
                    convertView = inflater.inflate(R.layout.date_item_recycler,
                            parent, false);
                    TextView dateIngredientTextView = (TextView) convertView.findViewById(R.id.date_ingredient);
                    TextView dateMassageTextView = (TextView) convertView.findViewById(R.id.date_message);

                    dateIngredientTextView.setText(dateListViewItem.getDateIngredient());
                    dateMassageTextView.setText(dateListViewItem.getDateExpiry());
                    break;

            }
        }

        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴
    @Override
    public long getItemId(int position) {
        return position;
    }

    // 지정한 위치(position)에 있는 데이터 리턴
    @Override
    public Object getItem(int position) {
        return DateListViewItemList.get(position);
    }

    // 아이템 추가를 위한 함수.
    public void addDateItem(String dateIngredient, String dateExpiry) {
        DateListViewItem item = new DateListViewItem();

        item.setType(ITEM_VIEW_TYPE_STRS);
        item.setDateIngredient(dateIngredient);
        item.setDateExpiry(dateExpiry);

        DateListViewItemList.add(item);
    }
}
