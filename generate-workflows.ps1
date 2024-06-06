$workflowTemplatePath = ".github\workflow-template.yaml"

$services = Get-ChildItem -Path . -Filter "*_service" -Directory

if ($services.Count -eq 0) {
    Write-Host "No services found ending with '_service' in the current directory."
    exit 1
}

$workflowTemplate = Get-Content $workflowTemplatePath -Raw

$workflowDirectory = ".github\workflows"

if (-not (Test-Path $workflowDirectory)) {
    New-Item -ItemType Directory -Path $workflowDirectory | Out-Null
}

foreach ($service in $services) {
    $serviceName = $service.Name
    $serviceNameUpper =$serviceName.ToUpper()
    $workflowContent = $workflowTemplate -replace "{{SERVICE_NAME}}", $serviceName -replace "{{SERVICE_NAME_UPPER}}", $serviceNameUpper
    $workflowFilePath = Join-Path -Path $workflowDirectory -ChildPath "$serviceName.yml"

    Set-Content -Path $workflowFilePath -Value $workflowContent

    Write-Host "Generating workflow for $serviceName service"
}
