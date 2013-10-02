package com.zaria.framework.services.i18n;

import java.io.IOException;
import java.io.InputStream;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class PropertyResourceBundleWithParent extends PropertyResourceBundle {
    public PropertyResourceBundleWithParent(InputStream stream) throws IOException {
        super(stream);
    }

    @Override
    public void setParent(ResourceBundle parent) {
        super.setParent(parent);
    }
}
