package dot.adun.vault.ui.components.core

import androidx.compose.runtime.Composable
import dot.adun.vault.ui.components.AddNewSiteDialog
import dot.adun.vault.ui.components.DeleteEmailDialog
import dot.adun.vault.ui.components.NewEmailDialog
import dot.adun.vault.ui.entities.DialogConfig

@Composable
fun VaultDialogManager(
    dialogConfig: DialogConfig?
) {
    if (dialogConfig == null) return

    when(dialogConfig) {
        is DialogConfig.DeleteEmail -> DeleteEmailDialog(
            onApprove = dialogConfig.onApprove,
            onCancel = dialogConfig.onCancel
        )
        is DialogConfig.EditEmail -> {  } // todo
        is DialogConfig.NewEmail -> NewEmailDialog(
            modifier = dialogConfig.modifier,
            addEmail = dialogConfig.addEmail,
            onClose = dialogConfig.onClose
        )
        is DialogConfig.NewSite -> AddNewSiteDialog(
            addSite = dialogConfig.addSite,
            onClose = dialogConfig.onClose
        )
    }
}