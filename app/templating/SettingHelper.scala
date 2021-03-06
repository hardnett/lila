package lila
package templating

import http.{ Context, Setting }
import ui.Theme

import play.api.templates.Html

trait SettingHelper {

  def setting(implicit ctx: Context) = new Setting(ctx)

  def soundString(implicit ctx: Context) = 
    setting(ctx).sound.fold("sound_state_on", "")

  def themeList = Theme.list
}
