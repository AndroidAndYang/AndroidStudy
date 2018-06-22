package com.seabig.moduledemo.common.ui.widget.dialogfragment;

import java.io.Serializable;

/**
 * @author YJZ
 */
public interface ViewConvertListener extends Serializable {

   long serialVersionUID = System.currentTimeMillis();

    /**
     * convert View
     *
     * @param holder view
     * @param dialog dialog
     */
   void convertView(ViewHolder holder, BaseDialogFragment dialog);
}
