package com.foke.together.external.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.provider.MediaStore
import com.foke.together.domain.interactor.GetCameraSourceTypeUseCase
import com.foke.together.domain.interactor.entity.CameraSourceType
import com.foke.together.domain.interactor.entity.CutFrameTypeV1
import com.foke.together.domain.output.ImageRepositoryInterface
import com.foke.together.util.AppPolicy
import com.foke.together.util.ImageFileUtil
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ImageRepository @Inject constructor(
    @ApplicationContext private val context: Context
): ImageRepositoryInterface{
    private var cutFrameType: CutFrameTypeV1 = CutFrameTypeV1.MAKER_FAIRE

    override fun getCutFrameType(): CutFrameTypeV1 = cutFrameType
    override suspend fun setCutFrameType(type: Int) {
        cutFrameType = CutFrameTypeV1.findBy(type)
    }

    override suspend fun cachingImage(image: Bitmap, fileName: String): Uri {
        return ImageFileUtil.cacheBitmap(context, image, fileName)
    }

    override fun getCachedImageUriList(sourceType: CameraSourceType): List<Uri> {
        var uriList = mutableListOf<Uri>()
        when(sourceType){
            CameraSourceType.EXTERNAL ->  {
                context.cacheDir.listFiles().forEach {
                    if(it.name.contains(AppPolicy.CAPTURED_FOUR_CUT_IMAGE_NAME)){
                        // capture로 시작하는 파일만 반환
                        uriList.add(Uri.fromFile(it))
                    }
                }
            }
            CameraSourceType.INTERNAL -> {
                val projection = arrayOf(
                    MediaStore.Images.Media._ID,
                    MediaStore.Images.Media.DISPLAY_NAME,
                    MediaStore.Images.Media.DATE_ADDED
                )
                val selection = "${MediaStore.Images.Media.RELATIVE_PATH} LIKE ?"
                val selectionArgs = arrayOf("%${AppPolicy.MEDIA_STORE_RELATIVE_PATH}%")
                val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"

                val cursor = context.contentResolver.query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    projection,
                    selection,
                    selectionArgs,
                    sortOrder
                )

                cursor?.use {
                    val idColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                    val latestUris = mutableListOf<Uri>()

                    var count = 0
                    while (it.moveToNext() && count < AppPolicy.CAPTURE_COUNT) {
                        val id = it.getLong(idColumn)
                        val contentUri = Uri.withAppendedPath(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            id.toString()
                        )
                        latestUris.add(contentUri)
                        count++
                    }

                    latestUris.reverse()
                    uriList.addAll(latestUris)
                }
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