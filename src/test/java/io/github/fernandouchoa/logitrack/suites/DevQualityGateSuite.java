package io.github.fernandouchoa.logitrack.suites;

import org.junit.platform.suite.api.ExcludeTags;
import org.junit.platform.suite.api.IncludeClassNamePatterns;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("LogiTrack Pro - DEV Quality Gate")
@SelectPackages({
        "io.github.fernandouchoa.logitrack.tests",
        "io.github.fernandouchoa.logitrack.e2e"
})
@IncludeClassNamePatterns({
        ".*LoginTests",
        ".*DashboardTests",
        ".*VehicleRegistrationTests",
        ".*MaintenanceSchedulingTests",
        ".*TripRegistrationTests",
        ".*FleetManagementE2ETests"
})
@ExcludeTags("known-defect")
public class DevQualityGateSuite {
}