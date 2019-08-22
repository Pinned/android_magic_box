package com.knero.android.tools.blackbox;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.knero.android.tools.blackbox.internal.BlackBoxFragment;
import com.knero.android.tools.blackbox.internal.BlackBoxImpl;
import com.knero.android.tools.blackbox.internal.Lazy;

/**
 * @author knero
 * @since 2019-08-21
 */
public class BlackBox {

    private static final String BLACK_BOX_TAG = "com.knero.android.tools.blackbox.fragment";

    public static BlackBoxImpl of(Fragment fragment) {
        return new BlackBoxImpl(getLazySingleton(fragment.getFragmentManager()));
    }

    public static BlackBoxImpl of(FragmentActivity activity) {
        return new BlackBoxImpl(getLazySingleton(activity.getSupportFragmentManager()));
    }

    @NonNull
    private static Lazy<BlackBoxFragment> getLazySingleton(@NonNull final FragmentManager fragmentManager) {
        return new Lazy<BlackBoxFragment>() {
            private BlackBoxFragment mBlackBoxFragment;

            @Override
            public synchronized BlackBoxFragment get() {
                if (mBlackBoxFragment == null) {
                    mBlackBoxFragment = getFragment(fragmentManager);
                }
                return mBlackBoxFragment;
            }
        };
    }

    private static BlackBoxFragment getFragment(@NonNull final FragmentManager fragmentManager) {
        BlackBoxFragment blackBoxFragment = findFragment(fragmentManager);
        boolean isNewInstance = blackBoxFragment == null;
        if (isNewInstance) {
            blackBoxFragment = new BlackBoxFragment();
            fragmentManager
                    .beginTransaction()
                    .add(blackBoxFragment, BLACK_BOX_TAG)
                    .commitNow();
        }
        return blackBoxFragment;
    }


    private static BlackBoxFragment findFragment(@NonNull final FragmentManager fragmentManager) {
        return (BlackBoxFragment) fragmentManager.findFragmentByTag(BLACK_BOX_TAG);
    }
    
}
