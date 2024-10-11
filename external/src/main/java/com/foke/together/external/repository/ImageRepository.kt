package com.foke.together.external.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.provider.MediaStore
import com.foke.together.domain.interactor.entity.CutFrameType
import com.foke.together.domain.output.ImageRepositoryInterface
import com.foke.together.util.AppPolicy
import com.foke.together.util.ImageFileUtil
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ImageRepository @Inject constructor(
    @ApplicationContext private val context: Context
): ImageRepositoryInterface{
    private var cutFrameType: CutFrameType = CutFrameType.MAKER_FAIRE

    override fun getCutFrameType(): CutFrameType = cutFrameType
    override suspend fun setCutFrameType(type: Int) {
        cutFrameType = CutFrameType.findBy(type)
    }

    override suspend fun cachingImage(image: Bitmap, fileName: String): Uri {
        return ImageFileUtil.cacheBitmap(context, image, fileName)
    }

    override fun getCachedImageUriList(): List<Uri> {
        var uriList = mutableListOf<Uri>()
        context.cacheDir.listFiles().forEach {
            if(it.name.contains(AppPolicy.CAPTURED_FOUR_CUT_IMAGE_NAME)){
                // capture로 시작하는 파일만 반환
                uriList.add(Uri.fromFile(it))
            }
        }
        return uriList
    }

    //TODO: file작업코드는 repository에서 하지말고 Util로 옮겨놓기
    override suspend fun clearCacheDir() {
        context.cacheDir.listFiles().forEach {
            if(it.name.contains(".jpg")){
                it.delete()
            }
        }
    }

    override suspend fun saveToStorage(image: Bitmap, fileName: String): Uri {
        return ImageFileUtil.saveBitmapToStorage(context, image, fileName)
    }

    override fun getUriToBitmap(imageUri: Uri): Bitmap {
        return ImageDecoder.decodeBitmap(ImageDecoder.createSource(context.contentResolver, imageUri))
    }

}