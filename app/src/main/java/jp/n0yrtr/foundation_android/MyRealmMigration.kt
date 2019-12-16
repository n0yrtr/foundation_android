package jp.n0yrtr.foundation_android

import io.realm.DynamicRealm
import io.realm.FieldAttribute
import io.realm.RealmMigration


class MyRealmMigration : RealmMigration {

    override fun migrate(realm: DynamicRealm, oldVersion: Long, newVersion: Long) {
        // Access the Realm schema in order to create, modify or delete classes and their fields.

        var migrateVersion = oldVersion
        val schema = realm.schema

        // Migrate from version 0 to version 1
        if (migrateVersion == 0L) {
            // version 1に変更の必要性がある場合にここにマイグレーションを実装する
            migrateVersion++
        }
    }
}