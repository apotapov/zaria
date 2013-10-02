package com.zaria.framework.es.components.utils;

import com.badlogic.gdx.artemis.Component;

public interface VisitableComponent extends Component {

    public void accept(ComponentVisitor visitor);

}
