package com.babarehner.taichilist.pojos;

/**
 * Project Name: Tai Chi List
 * <p>
 * Copyright 1/13/20 by Mike Rehner
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

// model class for the Exercise Header that is at the top of each list of exercises
public class ExerciseHeaderHorizontal {

    private String exerciseHeader;
    private int image;

    public ExerciseHeaderHorizontal(String exerciseHeader, int image){
        this.exerciseHeader = exerciseHeader;
        this.image = image;
    }

    public String getExerciseHeader(){
        return exerciseHeader;
    }

    public void setExerciseHeader(String exerciseHeader){
        this.exerciseHeader = exerciseHeader;
    }

    public int getImage() {return image;}

    public void setImage(int image) {this.image = image;}
}
