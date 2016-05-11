package pl.allegro.tdr.gruntmaven;

import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import pl.allegro.tdr.gruntmaven.executable.Executable;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Executes npm to run a build script (npm run build).
 *
 * @author bannwartt
 */
@Mojo(name = "npm-run", defaultPhase = LifecyclePhase.COMPILE, threadSafe = true)
public class ExecNpmRunMojo extends AbstractExecutableMojo {

  protected static final String NPM_RUN_COMMAND = "run build";

  /**
   * Name of npm executable in PATH, defaults to npm.
   */
  @Parameter(property = "npmExecutable", defaultValue = "npm")
  protected String npmExecutable;

  /**
   * List of additional options passed to npm when calling install.
   */
  @Parameter(property = "npmOptions")
  private String[] npmOptions;

  /**
   * Map of environment variables passed to npm install.
   */
  @Parameter
  protected Map<String, String> npmEnvironmentVar;

  @Override
  protected List<Executable> getExecutables() {
    Executable executable = new Executable(npmExecutable);

    executable.addEnvironmentVars(npmEnvironmentVar);

    executable.addArgument(NPM_RUN_COMMAND);
    appendNoColorsArgument(executable);
    appendNpmOptions(executable);

    return Arrays.asList(executable);
  }

  protected void appendNpmOptions(Executable executable) {
    executable.addNormalizedArguments(npmOptions, "=");
  }

  protected void appendNoColorsArgument(Executable executable) {
    if (!showColors) {
      executable.addArgument("--color=false");
    }
  }
}
