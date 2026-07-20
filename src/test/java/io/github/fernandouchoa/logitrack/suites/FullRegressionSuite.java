package io.github.fernandouchoa.logitrack.suites;

import org.junit.platform.suite.api.ExcludeTags;
import org.junit.platform.suite.api.IncludeClassNamePatterns;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("LogiTrack Pro - Full Regression")
@SelectPackages({
        "io.github.fernandouchoa.logitrack.tests",
        "io.github.fernandouchoa.logitrack.e2e"
})
@IncludeClassNamePatterns({
        ".*LoginTests",
        ".*AccessibilityTests",
        ".*RegistrationTests",
        ".*NavigationTests",
        ".*ApplicationMappingTests",
        ".*DashboardTests",
        ".*VehicleRegistrationTests",
        ".*DuplicateVehiclePlateTests",
        ".*MaintenanceSchedulingTests",
        ".*InvalidMaintenanceDateTests",
        ".*NegativeMaintenanceCostTests",
        ".*TripRegistrationTests",
        ".*FleetManagementE2ETests"
})
@ExcludeTags("known-defect")
public class FullRegressionSuite {
}