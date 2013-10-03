package com.roundtriangles.games.zaria.es.components.utils;

import com.artemis.Component;

public interface VisitableComponent extends Component {

    public void accept(ComponentVisitor visitor);

}
