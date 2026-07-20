param(
    [string]$Email = $env:LOGITRACK_EMAIL,
    [string]$Password = $env:LOGITRACK_PASSWORD,
    [string]$Test = "",
    [int]$RetryCount = 1,
    [switch]$Headless,
    [switch]$RunKnownDefects,
    [switch]$NoOpen
)

$ErrorActionPreference = "Stop"

if (-not (Test-Path ".\pom.xml")) {
    Write-Host "ERRO: execute na raiz do projeto." -ForegroundColor Red
    exit 1
}

$knownJdk = Join-Path `
    $env:USERPROFILE `
    "Documents\jdk-17.0.10"

if (Test-Path (Join-Path $knownJdk "bin\javac.exe")) {
    $env:JAVA_HOME = $knownJdk
    $env:Path = "$env:JAVA_HOME\bin;$env:Path"
}

if ([string]::IsNullOrWhiteSpace($Email)) {
    $Email = Read-Host "Informe o e-mail do ambiente"
}

if ([string]::IsNullOrWhiteSpace($Password)) {
    $securePassword = Read-Host `
        "Informe a senha do ambiente" `
        -AsSecureString

    $pointer =
        [Runtime.InteropServices.Marshal]::SecureStringToBSTR(
            $securePassword
        )

    try {
        $Password =
            [Runtime.InteropServices.Marshal]::PtrToStringBSTR(
                $pointer
            )
    } finally {
        [Runtime.InteropServices.Marshal]::ZeroFreeBSTR(
            $pointer
        )
    }
}

$headlessValue = if ($Headless) {
    "true"
} else {
    "false"
}

$openAllureValue = if ($NoOpen) {
    "false"
} else {
    "true"
}

$mavenArguments = @(
    "clean",
    "test",
    "-Demail=$Email",
    "-Dpassword=$Password",
    "-Dheadless=$headlessValue",
    "-Dtimeout=45000",
    "-Dtest.retry.count=$RetryCount",
    "-DopenAllure=$openAllureValue"
)

if (-not [string]::IsNullOrWhiteSpace($Test)) {
    $mavenArguments += "-Dtest=$Test"
}

if ($RunKnownDefects) {
    $mavenArguments += "-DrunKnownDefects=true"
}

Write-Host ""
Write-Host "==================================================" -ForegroundColor Cyan
Write-Host "EXECUTANDO TESTES LOGITRACK" -ForegroundColor Cyan
Write-Host "Paralelismo: 3 classes simultaneas"
Write-Host "Retry: $RetryCount"
Write-Host "Abrir Allure: $openAllureValue"
Write-Host "==================================================" -ForegroundColor Cyan
Write-Host ""

& mvn @mavenArguments
$testExitCode = $LASTEXITCODE

Write-Host ""
Write-Host "Pastas de evidencias:" -ForegroundColor Cyan
Write-Host "target\allure-results"
Write-Host "target\screenshots"
Write-Host "target\videos"
Write-Host "target\traces"
Write-Host "target\logs"

if ($testExitCode -ne 0) {
    Write-Host ""
    $failureMessage =
        "A suite terminou com falha. " +
        "O Allure sera aberto para analise."

    Write-Host $failureMessage -ForegroundColor Red
    exit $testExitCode
}

Write-Host ""
Write-Host "EXECUCAO CONCLUIDA COM SUCESSO." -ForegroundColor Green