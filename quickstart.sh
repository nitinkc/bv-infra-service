#!/bin/bash
# Quick Start Script for BitVelocity Infrastructure

set -e

echo "🚀 BitVelocity Infrastructure Quick Start"
echo "=========================================="
echo ""

# Check Java version
echo "Checking Java version..."
java -version 2>&1 | head -1
echo ""

# Build the project
echo "Building the project..."
./gradlew clean build
echo "✅ Build successful!"
echo ""

# Check for Pulumi
if ! command -v pulumi &> /dev/null; then
    echo "⚠️  Pulumi CLI is not installed."
    echo "Please install Pulumi from https://www.pulumi.com/docs/install/"
    echo ""
    echo "On macOS: brew install pulumi"
    echo "Or use the install script: curl -fsSL https://get.pulumi.com | sh"
    exit 1
fi

echo "Pulumi version:"
pulumi version
echo ""

# Initialize stack if needed
if [ ! -d ".pulumi" ]; then
    echo "Initializing Pulumi stack..."
    pulumi stack init dev --non-interactive || true
fi

# Set configuration
echo "Setting configuration for local development..."
pulumi config set cloudProvider local
pulumi config set regionEast us-east1
pulumi config set regionWest us-west1
pulumi config set meshEnabled false
pulumi config set replication.kafka.enabled false
pulumi config set db.orders.mode primary

echo "✅ Configuration set!"
echo ""

# Show current configuration
echo "Current stack configuration:"
pulumi config
echo ""

echo "🎉 Setup complete!"
echo ""
echo "Next steps:"
echo "  1. Preview infrastructure: pulumi preview"
echo "  2. Deploy infrastructure: pulumi up"
echo "  3. View outputs: pulumi stack output --json"
echo "  4. Destroy when done: pulumi destroy"
echo ""
echo "For more information, see README.md and IMPLEMENTATION.md"

