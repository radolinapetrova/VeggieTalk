# Define the path to the workflow template file
$workflowTemplatePath = ".github\workflow-template.yaml"

# Get the list of services ending with "_service" in the . directory
$services = Get-ChildItem -Path . -Filter "*_service" -Directory

# Check if any services are found
if ($services.Count -eq 0) {
    Write-Host "No services found ending with '_service' in the current directory."
    exit 1
}

# Read the content of the workflow template
$workflowTemplate = Get-Content $workflowTemplatePath -Raw

# Define the directory where workflow files will be placed
$workflowDirectory = ".github\workflows"

# Create the directory if it doesn't exist
if (-not (Test-Path $workflowDirectory)) {
    New-Item -ItemType Directory -Path $workflowDirectory | Out-Null
}

# Loop through each service
foreach ($service in $services) {
    $serviceName = $service.Name
    $serviceNameUpper =$serviceName.ToUpper()
    $workflowContent = $workflowTemplate -replace "{{SERVICE_NAME}}", $serviceName -replace "{{SERVICE_NAME_UPPER}}", $serviceNameUpper
    $workflowFilePath = Join-Path -Path $workflowDirectory -ChildPath "$serviceName.yml"
    
    # Write the workflow content to a file
    Set-Content -Path $workflowFilePath -Value $workflowContent

    Write-Host "Generating workflow for $serviceName service"
}
