package com.example.jetsnack.ui.home.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.jetsnack.model.SearchSuggestionGroup
import com.example.jetsnack.ui.theme.JetsnackTheme

@Composable
fun SearchSuggestion(suggestions: List<SearchSuggestionGroup>, onSuggestionSelect: (String) -> Unit) {
    LazyColumn() {
        suggestions.forEach { suggestionGroup ->
            item {
                SuggestionHeader(suggestionGroup.name)
            }
            items(suggestionGroup.suggestions) { suggestion ->
                Suggestion(suggestion, onSuggestionSelect, Modifier.fillParentMaxWidth())
            }
            item {
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}

@Composable
private fun SuggestionHeader(name: String, modifier: Modifier = Modifier) {
    Text(
        text = name,
        style = MaterialTheme.typography.titleLarge,
        color = JetsnackTheme.colors.textPrimary,
        modifier = modifier
            .heightIn(min = 56.dp)
            .padding(horizontal = 24.dp, vertical = 4.dp)
            .wrapContentHeight()
    )
}

@Composable
private fun Suggestion(suggestion: String, onSuggestionSelect: (String) -> Unit, modifier: Modifier = Modifier) {
    Text(
        text = suggestion,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier
            .clickable { onSuggestionSelect(suggestion) }
            .padding(start = 24.dp)
            .wrapContentSize(Alignment.CenterStart)
    )
}