package io.github.fernandouchoa.logitrack.suites;

import org.junit.platform.suite.api.IncludeClassNamePatterns;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("LogiTrack Pro - Known Defects")
@SelectPackages("io.github.fernandouchoa.logitrack.tests")
@IncludeClassNamePatterns({
        ".*InvalidTripDateTests",
        ".*InvalidTripDistanceTests"
})
public class KnownDefectsSuite {
}