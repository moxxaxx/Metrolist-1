package com.metrolist.music.selection

import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList

@Stable
class SelectionController {
    var inSelectionMode by mutableStateOf(false)
        private set

    // Use String keys for generality across screens
    private val _selected: SnapshotStateList<String> = mutableStateListOf()
    val selectedKeys: List<String> get() = _selected

    fun enterSelectionMode() {
        inSelectionMode = true
    }

    fun exitSelectionMode() {
        inSelectionMode = false
        _selected.clear()
    }

    fun toggleSelection(key: String) {
        if (_selected.contains(key)) _selected.remove(key) else _selected.add(key)
    }

    fun setSelected(keys: Collection<String>) {
        _selected.clear()
        _selected.addAll(keys)
    }

    fun selectAll(keys: Collection<String>) {
        _selected.clear()
        _selected.addAll(keys)
    }

    fun clearSelection() {
        _selected.clear()
    }

    fun isSelected(key: String): Boolean = key in _selected
    val selectionCount: Int get() = _selected.size
}

val LocalSelectionController = compositionLocalOf { SelectionController() }

@Composable
fun ProvideSelectionController(
    controller: SelectionController = remember { SelectionController() },
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalSelectionController provides controller) {
        content()
    }
}
