package com.example.breakingbadapp.utils

import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class ListDiffUtilTest {

    private var names = emptyList<String>()
    private val newData = listOf("Peter", "Claire", "Mick", "Hannah")

    private lateinit var listDiffUtil: ListDiffUtil<String>

    @Before
    fun setup() {
        listDiffUtil = ListDiffUtil(names, newData)
    }

    @Test
    fun `ListDiffUtil should returns correct names list size`() {
        assertThat(listDiffUtil.oldListSize).isEqualTo(names.size)
    }

    @Test
    fun `ListDiffUtil should returns correct newData list size`() {
        assertThat(listDiffUtil.newListSize).isEqualTo(newData.size)
    }

    @Test
    fun `names list should be different to newData list`() {
        val newListDiffUtil = ListDiffUtil(listOf("Kyle"), newData)
        assertThat(newListDiffUtil.areContentsTheSame(0, 0)).isFalse()
    }

    @Test
    fun `names list should be equal to newData list`() {
        val newListDiffUtil = ListDiffUtil(listOf("Peter"), newData)
        assertThat(newListDiffUtil.areContentsTheSame(0, 0)).isTrue()
    }
}