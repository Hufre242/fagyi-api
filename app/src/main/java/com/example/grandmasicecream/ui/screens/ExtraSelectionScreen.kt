package com.example.grandmasicecream.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.grandmasicecream.data.ExtraGroup
import com.example.grandmasicecream.data.ExtraItem

@Composable
fun ExtraSelectionScreen(
    extraGroups: List<ExtraGroup>,
    onConfirm: (List<Long>) -> Unit
) {
    val selectedExtras = remember {
        mutableStateMapOf<String, MutableSet<Long>>()
    }

    val selectedRadioIds = remember {
        mutableStateMapOf<String, MutableState<Long?>>()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        extraGroups.forEach { group ->

            if (group.required && !selectedRadioIds.containsKey(group.type)) {
                selectedRadioIds[group.type] = mutableStateOf(null)
            }

            item {
                Text(
                    text = group.type,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            if (group.required) {
                val selectedId = selectedRadioIds[group.type]!!

                items(group.items) { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = selectedId.value == item.id,
                                onClick = {
                                    selectedId.value = item.id
                                    selectedExtras[group.type] = mutableSetOf(item.id)
                                }
                            )
                            .padding(8.dp)
                    ) {
                        RadioButton(
                            selected = selectedId.value == item.id,
                            onClick = {
                                selectedId.value = item.id
                                selectedExtras[group.type] = mutableSetOf(item.id)
                            }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("${item.name} (+${item.price} Ft)")
                    }
                }
            } else {
                items(group.items) { item ->
                    val isChecked = remember { mutableStateOf(false) }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Checkbox(
                            checked = isChecked.value,
                            onCheckedChange = {
                                isChecked.value = it
                                val set = selectedExtras.getOrPut(group.type) { mutableSetOf() }
                                if (it) {
                                    set.add(item.id)
                                } else {
                                    set.remove(item.id)
                                }
                            }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("${item.name} (+${item.price} Ft)")
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        item {
            Button(
                onClick = {
                    val allSelectedIds = selectedExtras.values.flatten()
                    onConfirm(allSelectedIds)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Tovább a kosárhoz")
            }
        }
    }
}
