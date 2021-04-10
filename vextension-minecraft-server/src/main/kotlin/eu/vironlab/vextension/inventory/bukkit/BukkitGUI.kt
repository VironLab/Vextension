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
package eu.vironlab.vextension.inventory.bukkit

import eu.vironlab.vextension.bukkit.VextensionBukkit
import eu.vironlab.vextension.concurrent.scheduleAsync
import eu.vironlab.vextension.inventory.gui.GUI
import eu.vironlab.vextension.item.ItemStack
import eu.vironlab.vextension.item.extension.setItem
import eu.vironlab.vextension.util.ServerType
import eu.vironlab.vextension.util.ServerUtil
import eu.vironlab.vextension.util.UnsupportedServerTypeException
import org.bukkit.Bukkit
import java.lang.IllegalArgumentException
import java.util.*

class BukkitGUI(override val lines: Int, override val name: String) : GUI {
    var contents: MutableMap<Int, ItemStack> = mutableMapOf()
    override fun open(player: UUID) {
        if (ServerUtil.getServerType() != ServerType.BUKKIT)
            throw UnsupportedServerTypeException("BukkitGUI only supports Bukkit!")
        scheduleAsync {
            val inventory = Bukkit.createInventory(null, 9 * lines, name)
            Thread.sleep(50)
            for ((index: Int, item: ItemStack) in contents) {
                inventory.setItem(index, item)
            }
            Bukkit.getScheduler().runTask(VextensionBukkit.instance) { ->
                Bukkit.getPlayer(player)?.openInventory(inventory)
                    ?: throw IllegalArgumentException("Player doesn't exist")
            }
        }
    }


    fun setItem(slot: Int, item: ItemStack): BukkitGUI {
        contents[slot] = item
        return this
    }

    fun removeItem(slot: Int): BukkitGUI {
        contents.remove(slot)
        return this
    }


    override fun setBorder(border: ItemStack?): BukkitGUI {
        //<editor-fold desc="Border creation" defaultstate="collapsed">
        for (i: Int in 0..8) {
            if (border != null)
                contents[i] = border
            else contents.remove(i)
        }
        for (i: Int in lines * 9 - 9 until lines * 9) {
            if (border != null)
                contents[i] = border
            else contents.remove(i)
        }
        var i = 9
        while (i < 9 * lines) {
            if (border != null)
                contents[i] = border
            else contents.remove(i)
            if (i % 9 == 0) i += 8
            else i++
        }
        //</editor-fold>
        return this
    }

    fun addAllItems(itemList: Map<out Int, ItemStack>): BukkitGUI {
        this.contents.putAll(itemList)
        return this
    }

    fun addItem(item: ItemStack): BukkitGUI {
        var current = 0
        var slot: Int? = null
        for (i in contents.keys.toSortedSet().iterator()) {
            //println("$ia $current")
            if (i != current++) {
                slot = current.minus(1)
                break
            }
        }
        slot ?: return this
        println("$slot ${contents.containsKey(slot)}")
        contents.putIfAbsent(slot, item)
        return this
    }
}