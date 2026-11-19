import re

path = "app/src/main/java/com/shenhan/app/ui/screens/ProfileScreen.kt"
with open(path, 'r') as f:
    content = f.read()

# I will find the settings card end, and replace it with the inner content of the linked accounts card.

target_end_of_settings_card = """                    }
                }
            }
        }

        // 3rd-Party Linked Accounts & Multi-User Management Card
        item {
            com.shenhan.app.ui.components.FrostedGlassCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("profile_linked_accounts_card")
            ) {
                Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {"""

replacement = """                    }
                    
                    HorizontalDivider(color = FrostedGlassBorder)
                    
                    // 3rd-Party Linked Accounts & Multi-User Management
                    """

content = content.replace(target_end_of_settings_card, replacement)

# Now, we need to remove the closing tags of the Linked Accounts card. 
# At the end of the file, we have:
#                     }
#                 }
#             }
#         }
# 
#         item { Spacer(modifier = Modifier.height(16.dp)) }

# Since we merged two items into one, we just need to delete one level of `item { Card { Column {` which is `} } } }`
# Wait, let's just do it manually with sed or string replace.

target_end_of_file = """                    }
                }
            }
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }"""

replacement_end = """                    }
                }
            }
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }"""

# Wait, if we replace `target_end_of_settings_card` with `replacement`, we removed 1 `} } } } item { Card { Column {` sequence.
# That means the brackets are completely balanced!
# Let's verify:
# Before:
# ... Settings Content ...
#                 } // end Row
#             } // end Column
#         } // end Card
#     } // end item
#     item { // start item
#         Card { // start Card
#             Column { // start Column
#                 ... Linked Accounts Content ...

# After:
# ... Settings Content ...
#                 } // end Row
#                 HorizontalDivider(...)
#                 // 3rd-Party Linked Accounts & Multi-User Management
#                 ... Linked Accounts Content ...

# It is perfectly balanced! We don't need to change the end of the file!

with open(path, 'w') as f:
    f.write(content)
print("done")
