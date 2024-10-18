package org.turbanov.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.diagnostic.Logger

/**
 * @author Andrey Turbanov
 * @since 30.01.2017
 */
class CheckCIDRBuildAction : AnAction() {
  override fun actionPerformed(e: AnActionEvent) {
    val am = e.actionManager
    am.getActionIdList("Cidr").forEach { actionId ->
      LOG.info("CIDR ACTION_ID: $actionId")
    }
    val action = am.getAction("CidrBuildRunToolbar")
    LOG.info("Build action: $action")
    action.actionPerformed(e)

    //getAction("CidrBuildRunToolbar");
  }

  companion object {
    private val LOG = Logger.getInstance(
      CheckCIDRBuildAction::class.java
    )
  }
}
