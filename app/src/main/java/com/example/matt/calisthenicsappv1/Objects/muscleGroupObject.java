package com.example.matt.calisthenicsappv1.Objects;

/**
 * Created by Matt on 25/01/2018.
 */

public class muscleGroupObject {

    String muscleGroup;
    Boolean isSelected;

    public muscleGroupObject(String _muscleGroup, Boolean _selected)
    {
        super();
        this.muscleGroup = _muscleGroup;
        this.isSelected = _selected;
    }

    public String getMuscleGroup() {
        return muscleGroup;
    }

    public void setMuscleGroup(String muscleGroup) {
        this.muscleGroup = muscleGroup;
    }

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }
}
