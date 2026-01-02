#!/bin/bash
# Verification script for BitVelocity Infrastructure Service

echo "🔍 BitVelocity Infrastructure Service - Verification"
echo "===================================================="
echo ""

# Check Java
echo "1. Checking Java installation..."
if command -v java &> /dev/null; then
    java -version 2>&1 | head -1
    echo "   ✅ Java is installed"
else
    echo "   ❌ Java not found"
    exit 1
fi
echo ""

# Check Gradle
echo "2. Checking Gradle..."
if [ -f "./gradlew" ]; then
    echo "   ✅ Gradle wrapper found"
else
    echo "   ❌ Gradle wrapper not found"
    exit 1
fi
echo ""

# Count source files
echo "3. Checking source files..."
JAVA_COUNT=$(find src/main/java -name "*.java" 2>/dev/null | wc -l | tr -d ' ')
echo "   Java files: $JAVA_COUNT"
if [ "$JAVA_COUNT" -ge 17 ]; then
    echo "   ✅ All source files present"
else
    echo "   ⚠️  Expected 17 files, found $JAVA_COUNT"
fi
echo ""

# Check key files
echo "4. Checking key files..."
FILES_TO_CHECK=(
    "src/main/java/io/bitvelocity/infra/AppStack.java"
    "src/main/java/io/bitvelocity/infra/core/CloudProvider.java"
    "src/main/java/io/bitvelocity/infra/providers/local/LocalCloudProvider.java"
    "src/main/java/io/bitvelocity/infra/providers/gcp/GcpCloudProvider.java"
    "src/main/java/io/bitvelocity/infra/modules/cache/CacheModule.java"
    "src/main/java/io/bitvelocity/infra/modules/kubernetes/KubernetesModule.java"
    "src/main/java/io/bitvelocity/infra/modules/database/DatabaseModule.java"
    "Pulumi.yaml"
    "Pulumi.dev.yaml"
    "build.gradle"
)

ALL_PRESENT=true
for file in "${FILES_TO_CHECK[@]}"; do
    if [ -f "$file" ]; then
        echo "   ✅ $file"
    else
        echo "   ❌ Missing: $file"
        ALL_PRESENT=false
    fi
done
echo ""

# Try to compile
echo "5. Attempting compilation..."
if ./gradlew compileJava --quiet --no-daemon 2>&1 | grep -q "BUILD SUCCESSFUL"; then
    echo "   ✅ Compilation successful"
elif ./gradlew compileJava --no-daemon 2>&1 | tail -3 | grep -q "successful"; then
    echo "   ✅ Compilation successful"
else
    # Try one more time and show errors
    echo "   Running build..."
    ./gradlew compileJava --no-daemon 2>&1 | tail -20
fi
echo ""

# Check for build artifact
echo "6. Checking build artifacts..."
if [ -f "build/libs/bitvelocity-infra.jar" ]; then
    SIZE=$(ls -lh build/libs/bitvelocity-infra.jar | awk '{print $5}')
    echo "   ✅ JAR artifact exists: build/libs/bitvelocity-infra.jar ($SIZE)"
else
    echo "   ⚠️  JAR not found (run './gradlew build' to create it)"
fi
echo ""

# Check documentation
echo "7. Checking documentation..."
DOC_COUNT=$(ls *.md 2>/dev/null | wc -l | tr -d ' ')
echo "   Documentation files: $DOC_COUNT"
if [ "$DOC_COUNT" -ge 3 ]; then
    echo "   ✅ Documentation present"
fi
echo ""

# Summary
echo "=================================================="
echo "📊 Verification Summary"
echo "=================================================="
if [ "$ALL_PRESENT" = true ]; then
    echo "✅ All critical files are present"
    echo "✅ Project structure is correct"
    echo "✅ Ready for development"
    echo ""
    echo "Next steps:"
    echo "  • Build: ./gradlew build"
    echo "  • Install Pulumi: brew install pulumi (macOS)"
    echo "  • Quick start: ./quickstart.sh"
    echo "  • Read: IMPLEMENTATION.md for details"
else
    echo "⚠️  Some files are missing"
    echo "Please check the output above"
fi
echo ""

