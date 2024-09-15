package com.brk.basic.viewmodel

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.widget.AppCompatImageView
import com.brk.basic.R
import com.bumptech.glide.Glide

class LoadingDialog (context: Context, cancelable: Boolean) {
    var dialog: Dialog
    var prog: AppCompatImageView

    init {
        dialog = Dialog(context)
        dialog.setContentView(R.layout.progress_layout)
        dialog.setCancelable(cancelable)
        prog = dialog.findViewById(R.id.img_anim)
        Glide.with(context).asGif().load(R.raw.loading).into(prog)
    }

    fun getLoading(): Dialog {
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }
}