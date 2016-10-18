package agency.tango.materialintroscreen.widgets;

import android.content.Context;
import android.support.v4.view.CustomViewPager;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Scroller;

import agency.tango.materialintroscreen.SlideFragment;
import agency.tango.materialintroscreen.adapter.SlidesAdapter;

public class SwipeableViewPager extends CustomViewPager {
    float startPos = 0;
    private int currentIt;
    private boolean swipingAllowed;
    private Scroller scroller;

    public SwipeableViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        swipingAllowed = true;
    }

    @Override
    public boolean onInterceptTouchEvent(final MotionEvent event) {
        int action = MotionEventCompat.getActionMasked(event);

        switch (action) {
            case (MotionEvent.ACTION_DOWN):
                return super.onInterceptTouchEvent(event);
            case (MotionEvent.ACTION_MOVE):
                if (!swipingAllowed) {
                    return false;
                }
                return super.onInterceptTouchEvent(event);
            case (MotionEvent.ACTION_UP):
                if (!swipingAllowed) {
                    return false;
                }
                return super.onInterceptTouchEvent(event);
            default:
                return super.onInterceptTouchEvent(event);
        }
    }

    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        int action = MotionEventCompat.getActionMasked(event);

        switch (action) {
            case (MotionEvent.ACTION_DOWN):
                startPos = event.getX();
                currentIt = getCurrentItem();
                resolveSwipingRightAllowed();
                return super.onTouchEvent(event);
            case (MotionEvent.ACTION_MOVE):
                if (!swipingAllowed && startPos - event.getX() > 16) {
                    return true;
                }
                return super.onTouchEvent(event);
            case (MotionEvent.ACTION_UP):
                if (!swipingAllowed && startPos - event.getX() > 16) {
                    smoothScrollTo(getWidth() * currentIt, 0);
                    return true;
                }
                startPos = 0;
                return super.onTouchEvent(event);
            default:
                return super.onTouchEvent(event);
        }
    }

    @Override
    public SlidesAdapter getAdapter() {
        return (SlidesAdapter) super.getAdapter();
    }

    public int getPreviousItem() {
        return getCurrentItem() - 1;
    }

    public void setSwipingRightAllowed(boolean allowed) {
        swipingAllowed = allowed;
    }

    private void resolveSwipingRightAllowed() {
        SlideFragment fragment = getAdapter().getItem(getCurrentItem());
        if (!fragment.canMoveFurther() || fragment.hasNeededPermissionsToGrant()) {
            setSwipingRightAllowed(false);
        } else {
            setSwipingRightAllowed(true);
        }
    }
}