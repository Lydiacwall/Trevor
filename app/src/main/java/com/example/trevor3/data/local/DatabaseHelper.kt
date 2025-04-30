package com.example.trevor3.data.local
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.trevor3.domain.models.Tremor
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class DatabaseHelper(private val context : Context):
    SQLiteOpenHelper(context, DATABASE_NAME,null,DATABASE_VERSION) {

    // declare constants
    companion object {
        private const val DATABASE_NAME = "TremorDatabase.db"
        private const val DATABASE_VERSION = 2
        private const val TABLE_NAME = "users"

        private const val COLUMN_ID = "id"
        private const val COLUMN_START_TIME = "start_time"
        private const val COLUMN_END_TIME = "end_time"
        private const val COLUMN_DURATION = "duration"
        private const val COLUMN_INTENSITY = "intensity"
        private const val COLUMN_START_DATE = "start_date"
        private const val COLUMN_END_DATE = "end_date"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = ("CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_START_TIME REAL, " +
                "$COLUMN_END_TIME REAL, " +
                "$COLUMN_DURATION INTEGER, " +
                "$COLUMN_INTENSITY REAL," +
                "$COLUMN_START_DATE TEXT, " +
                "$COLUMN_END_DATE TEXT)"
                )

        db?.execSQL(createTableQuery)
        insertSampleData(db)

    }

    // Helper function to format a timestamp into a human-readable date string.
    private fun formatTimestamp(timestamp: Long): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return sdf.format(Date(timestamp))
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = " DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)

    }


//    fun clearTremorTable() {
//        val db = writableDatabase
//        db.execSQL("DELETE FROM $TABLE_NAME")
//        db.execSQL("DELETE FROM sqlite_sequence WHERE name='$TABLE_NAME'") // optional: resets autoincrement ID
//    }

    // Insert a tremor event into the database.
    private fun insertTremor(db: SQLiteDatabase,startTime: Long, endTime: Long, intensity: Float): Long {
        val duration = endTime - startTime
        val startDate = formatTimestamp(startTime)
        val endDate = formatTimestamp(endTime)
        val values = ContentValues().apply {
            put(COLUMN_START_TIME, startTime)
            put(COLUMN_END_TIME, endTime)
            put(COLUMN_DURATION, duration)
            put(COLUMN_INTENSITY, intensity)
            put(COLUMN_START_DATE, startDate)
            put(COLUMN_END_DATE, endDate)
        }
        return db.insert(TABLE_NAME, null, values)
    }

    fun insertTremor(startTime: Long, endTime: Long, intensity: Float): Long {
        return insertTremor(writableDatabase, startTime, endTime, intensity)
    }

    private fun insertSampleData(db: SQLiteDatabase?) {
        if (db == null) return


        fun getTimestamp(year: Int, month: Int, day: Int, hour: Int, minute: Int, second: Int): Long {
            val cal = Calendar.getInstance().apply {
                clear()
                set(year, month - 1, day, hour, minute, second)
            }
            return cal.timeInMillis
        }


        val todayCal = Calendar.getInstance()
        val currentYear = todayCal.get(Calendar.YEAR)
        val currentMonth = todayCal.get(Calendar.MONTH) + 1 // (Calendar.MONTH is 0-indexed)
        val todayDay = todayCal.get(Calendar.DAY_OF_MONTH)


        // --- Sample 1: Today at 12:00:00 ---
        val sample1Start = getTimestamp(currentYear, currentMonth, todayDay, 12, 0, 0)
        val sample1End = sample1Start + 60_000L
        insertTremor(db, sample1Start, sample1End, 2.0f)

        val sample11Start = getTimestamp(currentYear, currentMonth, todayDay, 6, 7, 0)
        val sample11End = sample11Start + 80_000L
        insertTremor(db, sample11Start, sample11End, 3.0f)

        val lateEveningStart = getTimestamp(currentYear, currentMonth, todayDay, 23, 25, 0) // 11:25:00 PM
        val lateEveningEnd = lateEveningStart + 10_000L
        insertTremor(db, lateEveningStart, lateEveningEnd, 3.0f)



        // --- Samples 2 & 3: Two for "this week"
        val sample2Start = getTimestamp(currentYear, currentMonth, todayDay - 2, 9, 0, 0)
        val sample2End = sample2Start + 78_000L
        insertTremor(db, sample2Start, sample2End, 2.7f)

        val sample3Start = getTimestamp(currentYear, currentMonth, todayDay - 1, 15, 0, 0)
        val sample3End = sample3Start + 69_000L
        insertTremor(db, sample3Start, sample3End, 8.3f)

        // --- Sample 4: One for January (use January 15) ---
        val sample4Start = getTimestamp(currentYear, 1, 15, 10, 30, 0)
        val sample4End = sample4Start + 47_000L
        insertTremor(db, sample4Start, sample4End, 7.0f)

        // --- Samples 5-8: Four for February ---
        val sample5Start = getTimestamp(currentYear, 2, 5, 8, 0, 0)
        val sample5End = sample5Start + 50_000L
        insertTremor(db, sample5Start, sample5End, 1.0f)

        val sample6Start = getTimestamp(currentYear, 2, 10, 14, 0, 0)
        val sample6End = sample6Start + 14_000L
        insertTremor(db, sample6Start, sample6End, 6.80f)

        val sample7Start = getTimestamp(currentYear, 2, 15, 18, 30, 0)
        val sample7End = sample7Start + 57_000L
        insertTremor(db, sample7Start, sample7End, 9.4f)

        val sample8Start = getTimestamp(currentYear, 2, 20, 21, 15, 0)
        val sample8End = sample8Start + 89_000L
        insertTremor(db, sample8Start, sample8End, 7.0f)

        // --- Samples 9-10: Two for March ---
        val sample9Start = getTimestamp(currentYear, 3, 10, 11, 0, 0)
        val sample9End = sample9Start + 30_000L
        insertTremor(db, sample9Start, sample9End, 4.8f)

        val sample10Start = getTimestamp(currentYear, 3, 25, 16, 30, 0)
        val sample10End = sample10Start + 40_000L
        insertTremor(db, sample10Start, sample10End, 6.6f)
    }


    // Function that retrieves tremor events by date (e.g., "2023-04-13")
    fun  getTremorsByDate(date: String): List<Tremor> {
            val tremors = mutableListOf<Tremor>()
            val db = readableDatabase


            val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_START_DATE LIKE ?"

            val cursor = db.rawQuery(query, arrayOf("$date%"))
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    val id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID))
                    val startTime = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_START_TIME))
                    val endTime = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_END_TIME))
                    val duration = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_DURATION))
                    val intensity = cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_INTENSITY))
                    val startDate =
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_START_DATE))
                    val endDate = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_END_DATE))
                    tremors.add(
                        Tremor(
                            id,
                            startTime,
                            endTime,
                            duration,
                            intensity,
                            startDate,
                            endDate
                        )
                    )
                }
                cursor.close()
            }
            return tremors
        }

    private fun getAverageDuration(query: String, selectionArgs: Array<String>): Double? {
            val db = readableDatabase
            val cursor = db.rawQuery(query, selectionArgs)
            var avg: Double? = null
            if (cursor != null && cursor.moveToFirst()) {
                avg = cursor.getDouble(cursor.getColumnIndexOrThrow("avgDuration"))
            }
            cursor?.close()
            return avg
        }

    fun getAverageDurationForDay(day: String): Double? {
            // We assume COLUMN_START_DATE is stored as "yyyy-MM-dd HH:mm:ss"
            val query =
                "SELECT AVG($COLUMN_DURATION) as avgDuration FROM $TABLE_NAME WHERE $COLUMN_START_DATE LIKE ?"
            // Wildcard to match any time on that day.
            return getAverageDuration(query, arrayOf("$day%"))
        }

    fun getAverageDurationForCurrentWeek(): Double? {
        val now = Calendar.getInstance()
        val year = now.get(Calendar.YEAR)
        val week = now.get(Calendar.WEEK_OF_YEAR)

        val calendar = Calendar.getInstance().apply {
            clear()
            firstDayOfWeek = Calendar.MONDAY
            set(Calendar.YEAR, year)
            set(Calendar.WEEK_OF_YEAR, week)
            set(Calendar.DAY_OF_WEEK, firstDayOfWeek)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        val startOfWeek = calendar.timeInMillis

        calendar.add(Calendar.DAY_OF_WEEK, 6)
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        calendar.set(Calendar.MILLISECOND, 999)
        val endOfWeek = calendar.timeInMillis

        val query =
            "SELECT AVG($COLUMN_DURATION) as avgDuration FROM $TABLE_NAME WHERE $COLUMN_START_TIME BETWEEN ? AND ?"
        return getAverageDuration(query, arrayOf(startOfWeek.toString(), endOfWeek.toString()))
    }


    fun getAverageDurationForSpecificWeek(year: Int, weekOfYear: Int): Double? {

            val calendar = Calendar.getInstance().apply {
                clear()
                set(Calendar.YEAR, year)
                set(Calendar.WEEK_OF_YEAR, weekOfYear)
                set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }
            val startOfWeek = calendar.timeInMillis


            calendar.add(Calendar.DAY_OF_WEEK, 6)
            calendar.set(Calendar.HOUR_OF_DAY, 23)
            calendar.set(Calendar.MINUTE, 59)
            calendar.set(Calendar.SECOND, 59)
            calendar.set(Calendar.MILLISECOND, 999)
            val endOfWeek = calendar.timeInMillis

            val query =
                "SELECT AVG($COLUMN_DURATION) as avgDuration FROM $TABLE_NAME WHERE $COLUMN_START_TIME BETWEEN ? AND ?"
            return getAverageDuration(query, arrayOf(startOfWeek.toString(), endOfWeek.toString()))
        }


        // ---------------------------
        // Helper Function for Aggregates
        // ---------------------------

    private fun getAverageValue(
            query: String,
            selectionArgs: Array<String>,
            columnAlias: String
        ): Double? {
            val db = readableDatabase
            val cursor = db.rawQuery(query, selectionArgs)
            var avg: Double? = null
            if (cursor != null && cursor.moveToFirst()) {
                avg = cursor.getDouble(cursor.getColumnIndexOrThrow(columnAlias))
            }
            cursor?.close()
            return avg
        }

        // Functions for Average Intensity


    fun getAverageIntensityForDay(year: Int, month: Int, day: Int): Double? {
            // Setup the calendar for the start of the day.
            val calendar = Calendar.getInstance().apply {
                clear() // Clear any previous settings.
                set(year, month - 1, day, 0, 0, 0)
                set(Calendar.MILLISECOND, 0)
            }
            val startOfDay = calendar.timeInMillis

            // Set the calendar to the end of the day.
            calendar.set(year, month - 1, day, 23, 59, 59)
            calendar.set(Calendar.MILLISECOND, 999)
            val endOfDay = calendar.timeInMillis

            // Build the query. This uses the raw timestamp (start_time) boundaries to filter events.
            val query =
                "SELECT AVG($COLUMN_INTENSITY) as avgIntensity FROM $TABLE_NAME WHERE $COLUMN_START_TIME BETWEEN ? AND ?"

            // Execute the query and return the average intensity.
            return getAverageValue(
                query,
                arrayOf(startOfDay.toString(), endOfDay.toString()),
                "avgIntensity"
            )
        }


    fun getAverageIntensityForSpecificWeek(year: Int, weekOfYear: Int): Double? {
            val calendar = Calendar.getInstance().apply {
                clear()
                set(Calendar.YEAR, year)
                set(Calendar.WEEK_OF_YEAR, weekOfYear)

                set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }
            val startOfWeek = calendar.timeInMillis


            calendar.add(Calendar.DAY_OF_WEEK, 6)
            calendar.set(Calendar.HOUR_OF_DAY, 23)
            calendar.set(Calendar.MINUTE, 59)
            calendar.set(Calendar.SECOND, 59)
            calendar.set(Calendar.MILLISECOND, 999)
            val endOfWeek = calendar.timeInMillis

            val query =
                "SELECT AVG($COLUMN_INTENSITY) as avgIntensity FROM $TABLE_NAME WHERE $COLUMN_START_TIME BETWEEN ? AND ?"
            return getAverageValue(
                query,
                arrayOf(startOfWeek.toString(), endOfWeek.toString()),
                "avgIntensity"
            )
        }

        // Helper function to count tremors using SQL COUNT(*)

    private fun getTremorCount(query: String, selectionArgs: Array<String>): Int {
        val db = readableDatabase
        val cursor = db.rawQuery(query, selectionArgs)
        var count = 0
        if (cursor != null && cursor.moveToFirst()) {
            count = cursor.getInt(cursor.getColumnIndexOrThrow("tremorCount"))
        }
        cursor?.close()
        return count
    }

        // Functions for Tremor Count

    fun getTremorCountForCurrentWeek(): ArrayList<Int> {
            val counts = ArrayList<Int>(7)
            val calendar = Calendar.getInstance().apply {
                firstDayOfWeek = Calendar.MONDAY
                set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }

            for (i in 0 until 7) {
                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH) + 1 // Calendar.MONTH is 0-based
                val day = calendar.get(Calendar.DAY_OF_MONTH)

                val count = getTremorCountForSpecificDay(year, month, day)
                counts.add(count)

                calendar.add(Calendar.DAY_OF_MONTH, 1) // Move to next day
            }

            return counts
        }

    fun getTremorCountForSpecificDay(year: Int, month: Int, day: Int): Int {
            val calendar = Calendar.getInstance().apply {
                // Calendar months are 0-indexed. Convert the provided month (1-indexed) by subtracting 1.
                clear()
                set(year, month - 1, day, 0, 0, 0)
                set(Calendar.MILLISECOND, 0)
            }
            val startOfDay = calendar.timeInMillis
            calendar.set(year, month - 1, day, 23, 59, 59)
            calendar.set(Calendar.MILLISECOND, 999)
            val endOfDay = calendar.timeInMillis

            val query =
                "SELECT COUNT(*) as tremorCount FROM $TABLE_NAME WHERE $COLUMN_START_TIME BETWEEN ? AND ?"
            return getTremorCount(query, arrayOf(startOfDay.toString(), endOfDay.toString()))
        }

    fun getTremorCountForSpecificWeek(year: Int, weekOfYear: Int): ArrayList<Int> {
            val counts = ArrayList<Int>(7)

            val calendar = Calendar.getInstance().apply {
                clear()
                set(Calendar.YEAR, year)
                set(Calendar.WEEK_OF_YEAR, weekOfYear)
                firstDayOfWeek = Calendar.MONDAY
                set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }

            for (i in 0 until 7) {
                val currentYear = calendar.get(Calendar.YEAR)
                val currentMonth = calendar.get(Calendar.MONTH) +1 // make it 1-indexed
                val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

                val count = getTremorCountForSpecificDay(currentYear, currentMonth, currentDay)
                counts.add(count)

                calendar.add(Calendar.DAY_OF_MONTH, 1)
            }

            return counts
        }

    }



