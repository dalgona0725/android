package com.theenm.android.viewholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.theenm.android.R;
import com.theenm.category.live.CategoryLiveFragment;
import com.theenm.category.vod.CategoryVODFragment;
import com.theenm.common.Constant;
import com.theenm.common.UserData;
import com.theenm.common.util.PopkonUtils;


public class PortalMenuHolder extends BaseViewHolder {
    private final LinearLayout mLinearCate;

    public static PortalMenuHolder newInstance(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_portal_menu, parent, false);
        return new PortalMenuHolder(itemView);
    }

    public PortalMenuHolder(View itemView) {
        super(itemView);

        mLinearCate = itemView.findViewById(R.id.linear_cate);
    }

    @Override
    public void onBindView(Object object, Context context, int position) {
        if(getItemViewType() == Constant.ReCyclerType.TYPE_POPKON_PORTAL_LIVE_MENU.ordinal() ||
                getItemViewType() == Constant.ReCyclerType.TYPE_POPKON_PORTAL_ADULT_MENU.ordinal()) {
            headerLiveCategory(itemView.getContext());
        } else if(getItemViewType() == Constant.ReCyclerType.TYPE_POPKON_PORTAL_VOD_MENU.ordinal()) {
            headerVODCategory(itemView.getContext());
        }
    }

    public class CategoryList
    {
        String mLabel = null;
        String mCode = null;
    }

    private void headerLiveCategory(Context context)
    {
        if( mLinearCate == null )
            return;

        mLinearCate.removeAllViews();

        CategoryList[] categoryList = null;

        if( UserData.getIsAdultCheck() )
        {
            categoryList = new CategoryList[UserData.mCategoryAdult.length];

            for( int i = 0; i < UserData.mCategoryAdult.length; i++ )
            {
                CategoryList category = new CategoryList();

                category.mLabel = UserData.mCategoryAdult[i].label;
                category.mCode = UserData.mCategoryAdult[i].code;

                categoryList[i] = category;
            }
        }
        else
        {
            categoryList = new CategoryList[UserData.mCategoryTeen.length];

            for( int i = 0; i < UserData.mCategoryTeen.length; i++ )
            {
                CategoryList category = new CategoryList();

                category.mLabel = UserData.mCategoryTeen[i].label;
                category.mCode = UserData.mCategoryTeen[i].code;

                categoryList[i] = category;
            }
        }

        int nDispWidth = PopkonUtils.getDeviecWidth(context);
        int nWidth = nDispWidth / 5;

        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(nWidth, ViewGroup.LayoutParams.WRAP_CONTENT);

        for( int i = 0; i < categoryList.length; i++ )
        {
            final CategoryList category = categoryList[i];
            {
                LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View v = layoutInflater.inflate(R.layout.new_category, null);
                v.setLayoutParams(layoutParams);

                ImageView btnCategory = (ImageView) v.findViewById(R.id.img_catecory);
                TextView txtCatecory = (TextView) v.findViewById(R.id.txt_catecory);
                LinearLayout mLinearCategory = (LinearLayout) v.findViewById(R.id.linear_category);
                // ImageView btnCategory = new ImageView( getActivity() );
                // btnCategory.setLayoutParams(layoutParams);
                // btnCategory.setScaleType(ImageView.ScaleType.CENTER);

                btnCategory.setScaleType(ImageView.ScaleType.CENTER);

                txtCatecory.setText(category.mLabel);

                int nCode = Integer.parseInt(category.mCode);

                switch( nCode )
                {
                    case 0:
                        btnCategory.setImageResource(R.drawable.ten_shorcut);
                        break;

                    case 2:
                    case 12:
                        btnCategory.setImageResource(R.drawable.icon_ten_cate_2);
                        break;
                    case 3:
                    case 13:
                        btnCategory.setImageResource(R.drawable.icon_ten_cate_3);
                        break;

                    case 4:
                    case 14:
                        btnCategory.setImageResource(R.drawable.icon_ten_cate_4);
                        break;
                    case 5:
                    case 16:
                        btnCategory.setImageResource(R.drawable.icon_ten_cate_5);
                        break;

                    // case 6:
                    // btnCategory.setImageResource(R.drawable.icon_ten_cate_5);
                    // break;

                    case 7:
                    case 10:
                        btnCategory.setImageResource(R.drawable.icon_ten_cate_7);
                        break;

                    case 8:
                    case 20:
                        btnCategory.setImageResource(R.drawable.icon_ten_cate_8);
                        break;

                    case 9:
                        btnCategory.setImageResource(R.drawable.icon_ten_cate_9);
                        break;

                    case 11:
                        btnCategory.setImageResource(R.drawable.ten_new);
                        break;

                    case 15:
                        btnCategory.setImageResource(R.drawable.icon_ten_cate_15);
                        break;

                    case 18:
                        btnCategory.setImageResource(R.drawable.icon_ten_cate_18);
                        break;

                    case 19:
                        btnCategory.setImageResource(R.drawable.icon_ten_cate_19);
                        break;

                    case 30:
                        btnCategory.setImageResource(R.drawable.icon_ten_cate_30);
                        break;

                    default:
                        // btnCategory.setImageResource(R.drawable.icon_ten_cate_19);
                        break;
                }

                mLinearCategory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        CategoryLiveFragment categoryLiveFragment = CategoryLiveFragment.newInstance(Integer.parseInt(category.mCode));
                        categoryLiveFragment.addFragment(context, R.id.fragment_container);
                    }
                });

                mLinearCate.addView(v);
            }
        }
    }

    private void headerVODCategory(Context context)
    {
        if( mLinearCate == null )
            return;

        mLinearCate.removeAllViews();

        CategoryList[] categoryList = null;

        if( UserData.mVODcategory != null && UserData.mVODcategory.length > 0 )
        {
            categoryList = new CategoryList[UserData.mVODcategory.length];
            for( int i = 0; i < UserData.mVODcategory.length; i++ )
            {
                CategoryList category = new CategoryList();
                category.mLabel = UserData.mVODcategory[i].label;
                category.mCode = UserData.mVODcategory[i].code;
                categoryList[i] = category;
            }
        }

        int nDispWidth = PopkonUtils.getDeviecWidth(context);
        int nWidth = nDispWidth / 5;

        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(nWidth, ViewGroup.LayoutParams.WRAP_CONTENT);

        LayoutInflater layoutVodInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewVOD = layoutVodInflater.inflate(R.layout.new_category, null);
        viewVOD.setLayoutParams(layoutParams);

        if( categoryList != null )
        {
            for( int i = 0; i < categoryList.length; i++ )
            {
                final CategoryList category = categoryList[i];

                LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View v = layoutInflater.inflate(R.layout.new_category, null);
                v.setLayoutParams(layoutParams);

                ImageView btnCategory = v.findViewById(R.id.img_catecory);
                TextView txtCatecory = v.findViewById(R.id.txt_catecory);
                LinearLayout mLinearCategory = v.findViewById(R.id.linear_category);

                btnCategory.setScaleType(ImageView.ScaleType.CENTER);
                txtCatecory.setText(category.mLabel);

                int nCode = Integer.parseInt(category.mCode);

                switch( nCode )
                {
                    case 1: // SPECIAL
                        btnCategory.setImageResource(R.drawable.icon_ten_cate_menu_2);
                        break;
                    case 2: // DANCE
                        btnCategory.setImageResource(R.drawable.icon_ten_cate_menu_3);
                        break;
                    case 3: // MUSIC
                        btnCategory.setImageResource(R.drawable.icon_ten_cate_menu_4);
                        break;
                    case 4: // TALK
                        btnCategory.setImageResource(R.drawable.icon_ten_cate_menu_5);
                        break;
                    case 6: // UCC
                        btnCategory.setImageResource(R.drawable.icon_ten_cate_menu_6);
                        break;
                    default: // 이벤트
                        btnCategory.setImageResource(R.drawable.icon_ten_cate_menu_7);
                        break;
                }

                mLinearCategory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        CategoryVODFragment categoryVODFragment = CategoryVODFragment.newInstance(Integer.parseInt(category.mCode));
                        categoryVODFragment.addFragment(context, R.id.fragment_container);
                    }
                });
                mLinearCate.addView(v);
            }
        }

        ImageView btnVODCategory = viewVOD.findViewById(R.id.img_catecory);
        TextView txtVODCatecory = viewVOD.findViewById(R.id.txt_catecory);
        btnVODCategory.setScaleType(ImageView.ScaleType.CENTER);
        txtVODCatecory.setText("다시보기");
        btnVODCategory.setImageResource(R.drawable.icon_ten_cate_menu_1);

        btnVODCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                CategoryVODFragment categoryVODFragment = CategoryVODFragment.newInstance(-1);
                categoryVODFragment.addFragment(context, R.id.fragment_container);
            }
        });
        mLinearCate.addView(viewVOD);
    }
}
