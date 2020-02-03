package com.babarehner.taichilist.pojos;

/**
 * Project Name: Tai Chi List
 * <p>
 * Copyright 2/3/20 by Mike Rehner
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

public class ExerciseItemVertical {

    private String exerciseItem;
    private int imageDirection;

    public ExerciseItemVertical (String exerciseItem, int imageDirection){
        this.exerciseItem= exerciseItem;
        this.imageDirection = imageDirection;
    }

    public String getExerciseItem(){
        return exerciseItem;
    }

    public void setExerciseItem(String exerciseItem){
        this.exerciseItem = exerciseItem;
    }

    public int getImageDirection() {return imageDirection;}

    public void setImageDirection(int imageDirection) {this.imageDirection = imageDirection;}
}
