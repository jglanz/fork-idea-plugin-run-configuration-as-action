package org.turbanov.actions

import com.intellij.execution.*
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.project.Project
import com.jetbrains.cidr.cpp.execution.CMakeAppRunConfiguration
import com.jetbrains.cidr.cpp.execution.build.CMakeBuild

/**
 * @author Andrey Turbanov
 * @since 30.01.2017
 */
class CheckCIDRBuildAction : AnAction() {
  override fun actionPerformed(e: AnActionEvent) {
    val project = e.project!!
    val rm = RunManager.getInstance(project)
    rm.allConfigurationsList.forEach { c ->
      if (c !is CMakeAppRunConfiguration)
        return@forEach

      val targetManager = ExecutionTargetManager.getInstance(project)
      val targets = targetManager.getTargetsFor(c)
      LOG.warn("Run config: ${c.name}")
      targets.forEach { t ->
        LOG.warn("Build Target: ${t.displayName}")
        ExecutionManager.getInstance(project)
        CMakeBuild.build(project,c.getBuildAndRunConfigurations(t)!!)
      }


//      LOG.warn("Build config (${c.javaClass.name}): ${c.name}")
//      RunConfigurationStartHistory.Companion.getInstance(project).register(c)

    }
//    val am = e.actionManager
//    am.getActionIdList("Cidr").forEach { actionId ->
//      LOG.info("CIDR ACTION_ID: $actionId")
//    }
//    val action = am.getAction("CidrBuildRunToolbar")
//    LOG.info("Build action: $action")
//    action.actionPerformed(e)
//
//    CidrBuildConfigurationProvider.EP_NAME.extensionList.forEach { p ->
//      val buildConfigs = p.getBuildableConfigurations(project)
//      LOG.info("Build provider ${p.javaClass.name}")
//      buildConfigs.forEach { bc ->
//        LOG.info("Build config ${bc.name}")
//
//      }
//    }
    //getAction("CidrBuildRunToolbar");
  }

//  private fun getExecutionTarget(project: Project, runConfig: RunnerAndConfigurationSettings): ExecutionTarget {
//    val targetManager = ExecutionTargetManager.getInstance(project)
//    val active = targetManager.activeTarget
//    if (executionTargetId == null || executionTargetId == active.id) {
//      return active //use selected as is
//    }
//
//    val targets = targetManager.getTargetsFor(runConfig.configuration)
//    for (target in targets) {
//      if (target.id == executionTargetId) {
//        return target
//      }
//    }
//    return active //fallback to active
//  }
//
  private fun getFirstTarget(project: Project, runConfig: RunnerAndConfigurationSettings): ExecutionTarget {
    return getTargets(project, runConfig)[0]!!
  }

  private fun getTargets(project: Project, runConfig: RunnerAndConfigurationSettings): List<ExecutionTarget?> {
    var targets = ExecutionTargetManager.getInstance(project).getTargetsFor(runConfig.configuration)

    if (targets.size == 1 && DefaultExecutionTarget.INSTANCE == targets[0]) {
      return targets
    }
    targets = ArrayList(targets)
    targets.add(null)
    return targets
  }


  companion object {
    private val LOG = Logger.getInstance(
      CheckCIDRBuildAction::class.java
    )
  }
}
