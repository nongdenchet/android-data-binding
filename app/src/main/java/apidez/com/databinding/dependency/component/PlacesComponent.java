package apidez.com.databinding.dependency.component;

import apidez.com.databinding.dependency.module.PlacesModule;
import apidez.com.databinding.dependency.scope.ViewScope;
import apidez.com.databinding.view.fragment.PlacesFragment;
import dagger.Subcomponent;

/**
 * Created by nongdenchet on 10/24/15.
 */
@ViewScope
@Subcomponent(modules = {PlacesModule.class})
public interface PlacesComponent {
    void inject(PlacesFragment placesFragment);
}
