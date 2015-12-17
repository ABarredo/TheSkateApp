package core.myapplication;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * need to be added if using the ClickListener Interface --> implements SkateAdapter.ClickListener
 */
public class NavigationDrawerFragment extends Fragment {

    private RecyclerView recyclerView;

    public static final String PREF_FILE_NAME="testpref";
    public static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;

    private boolean mUserLearnedDrawer = false;
    private boolean mFromSavedInstance;

    private View fragmentContainerView;

    private SkateAdapter skateAdapter;
    private String TAG = "Abarredo.NavigationDrawerFragment";
    public NavigationDrawerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserLearnedDrawer=Boolean.getBoolean(readFromSharedPreferences(getActivity(),KEY_USER_LEARNED_DRAWER,"false"));
        if(savedInstanceState != null){
            mFromSavedInstance = true;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        recyclerView = (RecyclerView) layout.findViewById(R.id.drawer_list);

        skateAdapter = new SkateAdapter(getActivity(),getData());
        //skateAdapter.setClickListener(this);

        recyclerView.setAdapter(skateAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),recyclerView,new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                switch(position){
                    case 0:
                        break;
                    case 1:
                        startActivity(new Intent(getActivity(),SubActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(getActivity(),CameraActivity.class));
                        break;
                }

            }

            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(getActivity(),"Item clicked at "+position, Toast.LENGTH_SHORT).show();
            }
        }));


        return layout;

    }

    public static List<DrawerItems> getData(){
        List<DrawerItems> items = new ArrayList<>();
        int [] icons = {R.drawable.ic_home_black_24dp,R.drawable.ic_person_black_24dp,R.drawable.ic_photo_camera_black_24dp};
        String [] titles = {"Inicio","Progreso","Camara"};
        for(int i = 0;i<titles.length && i<icons.length;i++){
            DrawerItems current = new DrawerItems();
            current.title = titles[i];
            current.icon = icons[i];
            items.add(current);
        }
        return items;

    }
    public void setUp(int fragmentID,DrawerLayout drawerLayout, final Toolbar toolbar) {
        fragmentContainerView = getActivity().findViewById(fragmentID);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(),mDrawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                Log.d(TAG, "Drawer Opened");
                if(!mUserLearnedDrawer){
                    mUserLearnedDrawer=true;
                    saveToSharedPreferences(getActivity(),KEY_USER_LEARNED_DRAWER,mUserLearnedDrawer+"");
                }
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if(slideOffset<0.6){
                    toolbar.setAlpha(1-slideOffset);
                }
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }
        };
        if(!mUserLearnedDrawer && !mFromSavedInstance){
            mDrawerLayout.openDrawer(fragmentContainerView);
        }
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }

    public static void saveToSharedPreferences(Context context,String preferenceName, String preferenceValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(preferenceName,preferenceValue);
        editor.apply();

    }
    public static String readFromSharedPreferences(Context context,String preferenceName, String defaultValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(preferenceName,defaultValue);
    }

    /* need to be added if using the ClickListener Interface :
    @Override
    public void itemClicked(View view, int position) {
        startActivity(new Intent(getActivity(),SubActivity.class));

    }*/
    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{
        private String TAG = "Abarredo.RecyclerTouchListener";
        private GestureDetector gestureDetector;
        private ClickListener clikListener;
        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener){
            this.clikListener = clickListener;
            Log.d(TAG, "Constructor invoked");
            gestureDetector = new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    Log.d(TAG, "Tap");
                    return true;

                }

                @Override
                public void onLongPress(MotionEvent e) {
                    super.onLongPress(e);
                    View child = recyclerView.findChildViewUnder(e.getX(),e.getY());
                    if(child!= null && clickListener!= null) {
                        clickListener.onLongClick(child,recyclerView.getChildPosition(child));
                        Log.d(TAG, "Long Press");
                    }
                }
            });

        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(),e.getY());
            if(child!=null && clikListener!=null && gestureDetector.onTouchEvent(e)){
                clikListener.onClick(child,rv.getChildPosition(child));
            }
            Log.d(TAG, "On intercept touch event");

            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            Log.d(TAG, "On touch event");

        }

    }
    public static interface ClickListener{
        public void onClick(View view,int position);
        public void onLongClick(View view,int position);
    }
}
