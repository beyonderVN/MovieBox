/*
 * Copyright (C) 2016 Appflate.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.longngo.moviebox.common.coremvp;

import java.io.Serializable;

/**
 * Every presenter in the app must either implement this interface or extend SimpleMVPPresenter
 * indicating the MVPView type that wants to be attached with.
 */
public interface MVPPresenter<V extends MVPView, M extends Serializable> {

    void attachView(V mvpView, M presentationModel);


    void detachView();

    void onDestroy();
}
