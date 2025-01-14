package com.idormy.sms.forwarder.database.dao

import androidx.paging.PagingSource
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.idormy.sms.forwarder.database.entity.Rule
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface RuleDao {

    @Insert
    fun insert(rule: Rule)

    @Delete
    fun delete(rule: Rule): Completable

    @Query("DELETE FROM Rule where id=:id")
    fun delete(id: Long)

    @Query("DELETE FROM Rule")
    fun deleteAll()

    @Update
    fun update(rule: Rule)

    @Query("SELECT * FROM Rule where id=:id")
    fun get(id: Long): Single<Rule>

    @Query("SELECT * FROM Rule where id=:id")
    fun getOne(id: Long): Rule

    @Query("SELECT count(*) FROM Rule where type=:type and status=:status")
    fun count(type: String, status: Int): Single<Int>

    @Transaction
    @Query("SELECT * FROM Rule where type=:type ORDER BY id DESC")
    fun pagingSource(type: String): PagingSource<Int, Rule>

    @Transaction
    @Query("SELECT * FROM Rule where type=:type and status=:status and (sim_slot='ALL' or sim_slot=:simSlot)")
    fun getRuleList(type: String, status: Int, simSlot: String): List<Rule>

    @Transaction
    @RawQuery(observedEntities = [Rule::class])
    fun getAllRaw(query: SupportSQLiteQuery): List<Rule>
}