package ru.dev.gbixahue.library.tools

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import ru.dev.gbixahue.library.android.log.Log

class ProgressBitmapFactory(
		private val full: Drawable,
		private val half: Drawable,
		private val blank: Drawable,
		private val size: Int) {

	var sizeMultiplier: Float = 1f
		set(value) {
			if (value <= 0) field = 1f
			else field = value
		}

	var space: Int = 1

	private val STAR_POSITION = 1
	private val bitmapHolder = mutableMapOf<String, Bitmap>()

	private val width = full.intrinsicWidth
	private val height = full.intrinsicHeight

	var configFunction: (Float, Int) -> Int = { value, star ->
		val diff = value - star
		if (diff > 0 || diff == 0f) 1 else if (diff < 0 && diff > -1) 0 else -1
	}

	fun create(value: Float): Bitmap {
		val bitmap = Bitmap.createBitmap(
				(((width + space) * size - space) * sizeMultiplier).toInt(),
				(height * sizeMultiplier).toInt(),
				Bitmap.Config.ARGB_8888)

		val canvas = Canvas(bitmap)
		val identifier = calculateIdentifier(value, size)
		return if (bitmapHolder.containsKey(identifier)) {
			bitmapHolder[identifier]!!
		} else {
			assemble(canvas, full, half, blank, value, STAR_POSITION, space)
			bitmapHolder[identifier] = bitmap
			Log.d(this, "add new bitmap, size: ${bitmapHolder.size}")
			return bitmap
		}
	}

	fun recycle() {
		bitmapHolder.forEach { it.value.recycle() }
		bitmapHolder.clear()
	}

	private fun calculateIdentifier(value: Float, size: Int): String {
		val builder = StringBuilder()
		for (index in 1..size) {
			builder.append(configFunction(value, index))
		}
		builder.append(sizeMultiplier)
		return builder.toString()
	}

	private fun assemble(canvas: Canvas, full: Drawable, half: Drawable, blank: Drawable, value: Float, star: Int, space: Int) {
		val result = configFunction(value, star)
		val nextDrawable = if (result > 0) full else if (result < 0) blank else half

		val countOfSpaces = star - 1
		val startOffset = countOfSpaces * width + countOfSpaces * space
		nextDrawable.setBounds(
				(startOffset * sizeMultiplier).toInt(),
				0,
				((startOffset + width) * sizeMultiplier).toInt(),
				(height * sizeMultiplier).toInt())
		nextDrawable.draw(canvas)
		if (star == size) return
		assemble(canvas, full, half, blank, value, star + 1, space)
	}

	/*
	companion object {
		fun createStars(context: Context, multiplier: Float): ProgressBitmapFactory {
			return ProgressBitmapFactory(
					context.drawableFrom(R.drawable.star_full)!!,
					context.drawableFrom(R.drawable.star_half)!!,
					context.drawableFrom(R.drawable.star_empty)!!,
					5).apply {
				space = -1
				sizeMultiplier = multiplier
				configFunction = { value, starPosition ->
					var returnValue = 1
					val diff = starPosition - value
					if (diff > 0) returnValue = -1
					if (diff > 0 && diff < 1) {
						returnValue = if (diff <= 0.5) 0 else -1
					}
					returnValue
				}
			}
		}

		fun createAggr(context: Context, multiplier: Float): ProgressBitmapFactory {
			return ProgressBitmapFactory(
					context.drawableFrom(R.drawable.flash_full)!!,
					context.drawableFrom(R.drawable.flash_full)!!,
					context.drawableFrom(R.drawable.flash_empty)!!,
					5
			).apply { sizeMultiplier = multiplier }
		}
	}
	*/
}