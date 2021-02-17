/**
 *   Copyright © 2020 | vironlab.eu | All Rights Reserved.<p>
 * <p>
 *      ___    _______                        ______         ______  <p>
 *      __ |  / /___(_)______________ _______ ___  / ______ ____  /_ <p>
 *      __ | / / __  / __  ___/_  __ \__  __ \__  /  _  __ `/__  __ \<p>
 *      __ |/ /  _  /  _  /    / /_/ /_  / / /_  /___/ /_/ / _  /_/ /<p>
 *      _____/   /_/   /_/     \____/ /_/ /_/ /_____/\__,_/  /_.___/ <p>
 *<p>
 *    ____  _______     _______ _     ___  ____  __  __ _____ _   _ _____ <p>
 *   |  _ \| ____\ \   / / ____| |   / _ \|  _ \|  \/  | ____| \ | |_   _|<p>
 *   | | | |  _|  \ \ / /|  _| | |  | | | | |_) | |\/| |  _| |  \| | | |  <p>
 *   | |_| | |___  \ V / | |___| |__| |_| |  __/| |  | | |___| |\  | | |  <p>
 *   |____/|_____|  \_/  |_____|_____\___/|_|   |_|  |_|_____|_| \_| |_|  <p>
 *<p>
 *<p>
 *   This program is free software: you can redistribute it and/or modify<p>
 *   it under the terms of the GNU General Public License as published by<p>
 *   the Free Software Foundation, either version 3 of the License, or<p>
 *   (at your option) any later version.<p>
 *<p>
 *   This program is distributed in the hope that it will be useful,<p>
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of<p>
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the<p>
 *   GNU General Public License for more details.<p>
 *<p>
 *   You should have received a copy of the GNU General Public License<p>
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.<p>
 *<p>
 *   Contact:<p>
 *<p>
 *     Discordserver:   https://discord.gg/wvcX92VyEH<p>
 *     Website:         https://vironlab.eu/ <p>
 *     Mail:            contact@vironlab.eu<p>
 *<p>
 */

package eu.vironlab.vextension.database.mongo

import eu.vironlab.vextension.database.DatabaseUtil
import eu.vironlab.vextension.database.RemoteConnectionData
import eu.vironlab.vextension.database.info.ObjectInformation
import eu.vironlab.vextension.document.Document
import eu.vironlab.vextension.document.DocumentManagement

fun RemoteConnectionData.toMongo(): String {
    return "mongodb://${user}:${password}@${host}:${port}/${database}"
}

fun org.bson.Document.toDocument(name: String): Document {
    return DocumentManagement.newJsonDocument(name, this.toJson())
}

fun <T> org.bson.Document.parse(clazz: Class<T>, instance: T): org.bson.Document {
    val info = DatabaseUtil.getInfo(clazz).orElseThrow { IllegalStateException("Cannot parse unregistered Class") }
    if (instance != null) {
        instance!!::class.java.declaredFields.forEach {
            if (!info.ignoredFields.contains(it.name) && it.name != info.keyField) {
                val name = if (info.specificNames.containsKey(it.name)) {
                    info.specificNames.get(it.name)!!
                } else {
                    it.name
                }
                it.isAccessible = true
                this.append(name, it.get(instance))
            }
        }
    }
    return this
}

fun <T> org.bson.Document.toInstance(clazz: Class<T>, info: ObjectInformation): T {
    val instance = clazz.getConstructor().newInstance()
    instance!!::class.java.declaredFields.forEach {
        if (!info.ignoredFields.contains(it.name)) {
            val name = if (info.specificNames.containsKey(it.name)) {
                info.specificNames.get(it.name)!!
            } else {
                it.name
            }
            it.isAccessible = true
            it.set(instance, this.get(name)!!)
        }
    }
    return instance
}

fun Document.toBson(): org.bson.Document {
    return org.bson.Document.parse(this.toJson())
}